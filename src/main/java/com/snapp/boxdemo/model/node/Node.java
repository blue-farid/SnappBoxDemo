package com.snapp.boxdemo.model.node;

import com.snapp.boxdemo.model.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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