package com.snapp.boxdemo.model.entity.node;

import com.snapp.boxdemo.model.entity.BoxOrder;
import com.snapp.boxdemo.model.entity.PriceRange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("destination_node")
public class DestinationNode extends Node {
    @Column
    @Enumerated(EnumType.STRING)
    private PriceRange priceRange;
    @ManyToOne(targetEntity = BoxOrder.class)
    private BoxOrder boxOrder;
}
