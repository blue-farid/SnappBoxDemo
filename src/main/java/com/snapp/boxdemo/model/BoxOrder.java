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
public class BoxOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client owner;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "node_id")
    private SourceNode source;
    @OneToMany(mappedBy = "boxOrder", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
