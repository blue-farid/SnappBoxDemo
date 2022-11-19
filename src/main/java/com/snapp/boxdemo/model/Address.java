package com.snapp.boxdemo.model;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String base;
    @Column
    private Integer houseNumber;
    @Column
    private Integer homeUnit;
}
