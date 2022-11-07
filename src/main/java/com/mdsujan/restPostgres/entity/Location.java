package com.mdsujan.restPostgres.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Data
@Table(name = "location")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id")
    Long id;

    @Column(name = "location_desc")
    String desc;

    @Column(name = "location_type")
    String type;

    @Column(name = "pickup_allowed")
    boolean pickup_allowed;

    @Column(name = "shipping_allowed")
    boolean shipping_allowed;

    @Column(name = "delivery_allowed")
    boolean delivery_allowed;

    @Column(name = "address_line_1")
    String addrLine1;

    @Column(name = "address_line_2")
    String addrLine2;

    @Column(name = "address_line_3")
    String addrLine3;

    @Column(name = "city")
    String city;

    @Column(name = "state")
    String state;
}
