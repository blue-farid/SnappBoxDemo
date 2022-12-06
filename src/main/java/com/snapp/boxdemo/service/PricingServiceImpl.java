package com.snapp.boxdemo.service;

import com.snapp.boxdemo.model.dto.BaseResponseDto;
import com.snapp.boxdemo.model.dto.BoxPriceResponseDto;
import com.snapp.boxdemo.model.entity.node.DestinationNode;
import com.snapp.boxdemo.model.entity.node.SourceNode;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@AllArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final RestTemplate restTemplate;

    @Override
    public Double callPriceService(SourceNode sourceNode, List<DestinationNode> destinationNodes) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/price/box")
                .queryParam("sourceX", sourceNode.getX())
                .queryParam("sourceY", sourceNode.getY())
                .queryParam("destinationX", destinationNodes.stream().map(DestinationNode::getX).toArray())
                .queryParam("destinationY", destinationNodes.stream().map(DestinationNode::getY).toArray())
        .build();

        ResponseEntity<BaseResponseDto> response = restTemplate.getForEntity(uriComponents.toUri(), BaseResponseDto.class);
        BoxPriceResponseDto dto = (BoxPriceResponseDto) response.getBody().getResult();
        return Double.parseDouble(dto.getPriceAmount());
    }
}
