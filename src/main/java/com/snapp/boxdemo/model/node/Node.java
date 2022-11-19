package com.snapp.boxdemo.model.node;

import com.snapp.boxdemo.model.Address;

import javax.persistence.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String fullName;
    @Column
    private String phoneNumber;
    @OneToOne
    private Address address;
    private String comment;
}
