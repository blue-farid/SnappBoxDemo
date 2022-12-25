package com.snapp.boxdemo.model.entity.node;

import com.snapp.boxdemo.model.entity.BoxOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("source_node")
public class SourceNode extends Node {
    @OneToOne(mappedBy = "source")
    private BoxOrder boxOrder;
}
