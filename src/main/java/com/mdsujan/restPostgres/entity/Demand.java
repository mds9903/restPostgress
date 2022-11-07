package com.mdsujan.restPostgres.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "demand")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Demand {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "demand_id")
    Long id;

    @Column(name = "demand_type")
    String type;

    @Column(name = "quantity")
    int qty;

    @OneToOne
    @JoinColumn(name = "item_id")
    Item item;

    @OneToOne
    @JoinColumn(name = "location_id")
    Location location;
}
