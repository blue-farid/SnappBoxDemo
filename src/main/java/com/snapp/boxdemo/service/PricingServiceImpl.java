package com.snapp.boxdemo.service;

import com.snapp.boxdemo.model.dto.BaseResponseDto;
import com.snapp.boxdemo.model.entity.node.DestinationNode;
import com.snapp.boxdemo.model.entity.node.SourceNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PricingServiceImpl implements PricingService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${price.service.port}")
    private String priceServicePort;
    @Value("${price.service.host}")
    private String priceServiceHost;

    @Value("${price.service.scheme}")
    private String priceScheme;

    @Override
    public Double callPriceService(SourceNode sourceNode, List<DestinationNode> destinationNodes) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(priceScheme)
                .host(priceServiceHost)
                .port(priceServicePort)
                .path("/price/box")
                .queryParam("sourceX", sourceNode.getX())
                .queryParam("sourceY", sourceNode.getY())
                .queryParam("destinationX", destinationNodes.stream().map(DestinationNode::getX).toArray())
                .queryParam("destinationY", destinationNodes.stream().map(DestinationNode::getY).toArray())
        .build();

        LinkedHashMap<String, String> response = (LinkedHashMap<String, String>) restTemplate.getForObject(uriComponents.toUri(), BaseResponseDto.class).getResult();
        return Double.parseDouble(response.get("priceAmount"));
    }
}
