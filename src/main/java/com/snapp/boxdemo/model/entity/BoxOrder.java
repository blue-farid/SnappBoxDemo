package com.snapp.boxdemo.model.entity;

import com.snapp.boxdemo.model.entity.node.DestinationNode;
import com.snapp.boxdemo.model.entity.node.SourceNode;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client owner;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "source_node_id", referencedColumnName = "id")
    private SourceNode source;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_node_id", referencedColumnName = "id")
    private List<DestinationNode> destinations = new ArrayList<>();
    @Column
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    @Column(nullable = false)
    private Double price;
    @CreationTimestamp
    @Column
    private Date creationDate;
    @UpdateTimestamp
    @Column
    private Date lastModifiedDate;
}
