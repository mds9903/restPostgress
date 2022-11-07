package com.mdsujan.restPostgres.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Demand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item")
    private Long id;

    @Column(name = "")


}
