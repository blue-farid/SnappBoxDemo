package com.snapp.boxdemo.service;

import com.snapp.boxdemo.model.dto.BaseResponseDto;
import com.snapp.boxdemo.model.dto.BoxPriceResponseDto;
import com.snapp.boxdemo.model.entity.OrderType;
import com.snapp.boxdemo.model.entity.node.DestinationNode;
import com.snapp.boxdemo.model.entity.node.SourceNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final WebClient webClient;

    @Value("${price.service.port}")
    private String port;
    @Value("${price.service.host}")
    private String host;

    @Value("${price.service.scheme}")
    private String scheme;

    @Override
    public Double callPriceService(SourceNode sourceNode, List<DestinationNode> destinationNodes, OrderType type) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path("/price/box")
                .queryParam("sourceX", sourceNode.getX())
                .queryParam("sourceY", sourceNode.getY())
                .queryParam("destinationX", destinationNodes.stream().map(DestinationNode::getX).toArray())
                .queryParam("destinationY", destinationNodes.stream().map(DestinationNode::getY).toArray())
                .queryParam("orderTypeValue", type)
                .build();

        BaseResponseDto<BoxPriceResponseDto> responseDto = webClient.get()
                .uri(uriComponents.toUri())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponseDto<BoxPriceResponseDto>>() {})
                .block();

        return Double.parseDouble(responseDto.getResult().getPriceAmount());
    }
}
