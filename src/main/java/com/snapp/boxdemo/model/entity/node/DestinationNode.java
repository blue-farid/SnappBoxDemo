package com.snapp.boxdemo.model.entity.node;

import com.snapp.boxdemo.model.entity.BoxOrder;
import com.snapp.boxdemo.model.entity.PriceRange;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class DestinationNode extends Node {
    @Column
    @Enumerated(EnumType.STRING)
    private PriceRange priceRange;
    @ManyToOne
    @JoinColumn(name = "box_order_id")
    private BoxOrder boxOrder;
}
