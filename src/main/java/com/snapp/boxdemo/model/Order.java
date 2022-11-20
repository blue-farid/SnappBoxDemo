package com.snapp.boxdemo.model;

import com.snapp.boxdemo.model.node.DestinationNode;
import com.snapp.boxdemo.model.node.SourceNode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client owner;
    @OneToOne
    @JoinColumn(name = "node_id")
    private SourceNode source;
    @OneToMany(mappedBy = "order")
    private List<DestinationNode> destinations;
    @Column
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    @CreationTimestamp
    @Column
    private Date creationDate;
    @UpdateTimestamp
    @Column
    private Date lastModifiedDate;
}