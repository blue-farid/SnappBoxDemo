package com.snapp.boxdemo.model.entity.node;

import com.snapp.boxdemo.model.entity.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public abstract class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String fullName;
    @Column
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @Column
    private String comment;
}