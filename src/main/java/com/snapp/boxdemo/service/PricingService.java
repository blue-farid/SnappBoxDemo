package com.snapp.boxdemo.service;

import com.snapp.boxdemo.model.entity.node.DestinationNode;
import com.snapp.boxdemo.model.entity.node.SourceNode;

import java.util.List;

public interface PricingService {
    Double callPriceService(SourceNode sourceNode, List<DestinationNode> destinationNodes);
}
