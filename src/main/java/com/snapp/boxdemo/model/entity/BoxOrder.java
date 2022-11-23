package com.snapp.boxdemo.model.entity;

import com.snapp.boxdemo.model.entity.node.DestinationNode;
import com.snapp.boxdemo.model.entity.node.SourceNode;
import lombok.Builder;
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
@Builder
public class BoxOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client owner;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "source_node_id")
    private SourceNode source;
    @OneToMany(mappedBy = "boxOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
