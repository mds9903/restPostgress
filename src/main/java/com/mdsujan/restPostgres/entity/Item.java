package com.mdsujan.restPostgres.entity;

import com.mdsujan.restPostgres.request.CreateItemRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @Column(name = "item_id")
    private Long itemId;

    @Setter
    @Column(name = "item_desc")
    private String itemDesc;

    @Setter
    @Column(name = "category")
    private String category;

    @Setter
    @Column(name = "item_type")
    private String type;

    @Setter
    @Column(name = "item_status")
    private String status;

    @Setter
    @Column(name = "price")
    private Double price;

    @Setter
    @Column(name = "pickup_allowed")
    private Boolean pickupAllowed;

    @Setter
    @Column(name = "shipping_allowed")
    private Boolean shippingAllowed;

    @Setter
    @Column(name = "delivery_allowed")
    private Boolean deliveryAllowed;

    public Item(CreateItemRequest createItemRequest) {
        this.itemId = createItemRequest.getId();
        this.itemDesc = createItemRequest.getDesc();
        this.category = createItemRequest.getCategory();
        this.type = createItemRequest.getType();
        this.status = createItemRequest.getStatus();
        this.price = createItemRequest.getPrice();
        this.pickupAllowed = createItemRequest.getPickupAllowed();
        this.shippingAllowed=createItemRequest.getShippingAllowed();
        this.deliveryAllowed = createItemRequest.getDeliveryAllowed();
    }
}