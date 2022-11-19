package com.snapp.boxdemo.model.node;

import com.snapp.boxdemo.model.Order;
import com.snapp.boxdemo.model.PriceRange;

import javax.persistence.*;

@Entity
public class DestinationNode extends Node {
    @Column
    @Enumerated(EnumType.STRING)
    private PriceRange priceRange;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
