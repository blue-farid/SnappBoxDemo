package com.snapp.boxdemo.model.entity.node;

import com.snapp.boxdemo.model.entity.BoxOrder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class SourceNode extends Node {
    @OneToOne
    @JoinColumn(name = "box_order_id")
    private BoxOrder boxOrder;
}
