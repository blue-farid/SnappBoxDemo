package com.snapp.boxdemo.model;

import com.snapp.boxdemo.model.node.DestinationNode;
import com.snapp.boxdemo.model.node.SourceNode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
@Getter
@Setter
public class Order {
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person owner;
    private SourceNode source;
    private List<DestinationNode> destinations;
    private OrderType orderType;
}
