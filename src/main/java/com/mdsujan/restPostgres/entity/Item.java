package com.mdsujan.restPostgres.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "item", schema = "public")
public class Item {

    @Id
    @Column(name = "item_id")
    @NotNull
    private Long itemId;

    @Column(name = "item_desc")
    @NotNull
    private String itemDesc;

    @Column(name = "category")
    @NotNull
    private String category;

    @Column(name = "type")
    @NotNull
    private String itemType;

    @Column(name = "status")
    @NotNull
    private String status;

    @Column(name = "price")
    @NotNull
    private Double price;

    @Column(name = "pickup_allowed")
    @NotNull
    private Boolean pickupAllowed;

    @Column(name = "shipping_allowed")
    @NotNull
    private Boolean shippingAllowed;

    @Column(name = "delivery_allowed")
    @NotNull
    private Boolean deliveryAllowed;
}
