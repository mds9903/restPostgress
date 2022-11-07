package com.mdsujan.restPostgres.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "demand")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Supply {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "supply_id")
    Long id;

    @Column(name = "supply_type")
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
