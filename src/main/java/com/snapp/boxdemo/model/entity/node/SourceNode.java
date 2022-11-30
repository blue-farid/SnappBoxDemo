package com.snapp.boxdemo.model.entity.node;

import com.snapp.boxdemo.model.entity.BoxOrder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("source_node")
public class SourceNode extends Node {
    @OneToOne
    @JoinColumn(name = "box_order_id")
    private BoxOrder boxOrder;
}
