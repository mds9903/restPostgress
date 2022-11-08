package com.mdsujan.restPostgres.entity;

import com.mdsujan.restPostgres.request.CreateItemRequest;
import com.mdsujan.restPostgres.request.UpdateItemRequest;
import lombok.*;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import javax.persistence.*;

@Entity
@Data
@Table(name = "item",schema = "public")
@NoArgsConstructor
public class Item {
    @Id
    @Column(name = "item_id")
    private Long itemId;


    @Column(name = "item_desc")
    private String itemDesc;


    @Column(name = "category")
    private String category;


    @Column(name = "type")
    private String type;


    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private Double price;

    @Column(name = "pickup_allowed")
    private Boolean pickupAllowed;

    @Column(name = "shipping_allowed")
    private Boolean shippingAllowed;

    @Column(name = "delivery_allowed")
    private Boolean deliveryAllowed;

////
//    @OneToOne(mappedBy = "item")
//    private Supply supply;
//
//    @OneToOne(mappedBy = "item")
//    private Demand demand;

    public Item(CreateItemRequest createItemRequest) {
        this.itemId = createItemRequest.getId();
        this.itemDesc = createItemRequest.getDesc();
        this.category = createItemRequest.getCategory();
        this.type = createItemRequest.getType();
        this.status = createItemRequest.getStatus();
        this.price = createItemRequest.getPrice();
        this.pickupAllowed = createItemRequest.getPickupAllowed();
        this.shippingAllowed = createItemRequest.getShippingAllowed();
        this.deliveryAllowed = createItemRequest.getDeliveryAllowed();
    }

    public Item(UpdateItemRequest updateItemRequest) {
        this.itemId = updateItemRequest.getId();
        this.itemDesc=updateItemRequest.getDesc();
        this.category = updateItemRequest.getCategory();
        this.type = updateItemRequest.getType();
        this.status = updateItemRequest.getStatus();
        this.price = updateItemRequest.getPrice();
        this.pickupAllowed = updateItemRequest.getPickupAllowed();
        this.shippingAllowed = updateItemRequest.getShippingAllowed();
        this.deliveryAllowed = updateItemRequest.getDeliveryAllowed();
    }
}
