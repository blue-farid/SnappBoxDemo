package com.snapp.boxdemo.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column
    private String base;
    @Column
    private String houseNumber;
    @Column
    private String homeUnit;
}
