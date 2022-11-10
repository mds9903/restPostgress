package com.mdsujan.restPostgres.entity;

import com.mdsujan.restPostgres.request.CreateLocationRequest;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Data
@Table(name = "location", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Location {
    @Id
    @Column(name = "location_id")
//    Long locationId;
    Long id;

    @Column(name = "location_desc")
    String locationDesc;

    @Column(name = "type")
    String type;

    @Column(name = "pickup_allowed")
    boolean pickupAllowed;

    @Column(name = "shipping_allowed")
    boolean shippingAllowed;

    @Column(name = "delivery_allowed")
    boolean deliveryAllowed;

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

    @Column(name = "country")
    String country;

    @Column(name = "pincode")
    String pincode;

    public Location(CreateLocationRequest createLocationRequest) {
//        this.locationId=createLocationRequest.getId();
        this.id=createLocationRequest.getId();
        this.locationDesc=createLocationRequest.getDesc();
        this.type= createLocationRequest.getType();
        this.pickupAllowed = createLocationRequest.getPickupAllowed();
        this.shippingAllowed = createLocationRequest.getShippingAllowed();
        this.deliveryAllowed = createLocationRequest.getDeliveryAllowed();
        this.addrLine1=createLocationRequest.getAddrLine1();
        this.addrLine2=createLocationRequest.getAddrLine2();
        this.addrLine3=createLocationRequest.getAddrLine3();
        this.city=createLocationRequest.getCity();
        this.state=createLocationRequest.getState();
        this.country=createLocationRequest.getCountry();
        this.pincode = createLocationRequest.getPincode();

    }
}
