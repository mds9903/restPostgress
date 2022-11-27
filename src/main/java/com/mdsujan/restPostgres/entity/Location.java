package com.mdsujan.restPostgres.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "location", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Location {
    @Id
    @Column(name = "location_id")
    @NotNull
    Long locationId;

    @Column(name = "location_desc")
    @NotNull
    String locationDesc;

    @Column(name = "type")
    @NotNull
    String type;

    @Column(name = "pickup_allowed")
    @NotNull
    boolean pickupAllowed;

    @Column(name = "shipping_allowed")
    @NotNull
    boolean shippingAllowed;

    @Column(name = "delivery_allowed")
    @NotNull
    boolean deliveryAllowed;

    @Column(name = "address_line_1")
    @NotNull
    String addrLine1;

    @Column(name = "address_line_2")
    @NotNull
    String addrLine2;

    @Column(name = "address_line_3")
    @NotNull
    String addrLine3;

    @Column(name = "city")
    @NotNull
    String city;

    @Column(name = "state")
    @NotNull
    String state;

    @Column(name = "country")
    @NotNull
    String country;

    @Column(name = "pincode")
    @NotNull
    String pincode;


    public Location(long locationId,
                    String locationDesc,
                    String type,
                    boolean pickupAllowed,
                    boolean shippingAllowed,
                    boolean deliveryAllowed,
                    String city,
                    String state,
                    String country,
                    String pincode) {
        this.locationId = locationId;
        this.locationDesc = locationDesc;
        this.type = type;
        this.pickupAllowed = pickupAllowed;
        this.shippingAllowed = shippingAllowed;
        this.deliveryAllowed = deliveryAllowed;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }
}
