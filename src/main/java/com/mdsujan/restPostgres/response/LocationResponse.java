package com.mdsujan.restPostgres.response;

import com.mdsujan.restPostgres.entity.Location;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationResponse {

    Long id;

    String desc;

    String type;

    Boolean pickupAllowed;

    Boolean shippingAllowed;

    Boolean deliveryAllowed;

    String addrLine1;

    String addrLine2;

    String addrLine3;

    String city;

    String state;

    String country;
    String pincode;

    public LocationResponse(Location location) {
        this.id=location.getLocationId();
//        this.id=location.getId();
        this.desc=location.getLocationDesc();
        this.type= location.getType();
        this.pickupAllowed = location.isPickupAllowed();
        this.shippingAllowed = location.isShippingAllowed();
        this.deliveryAllowed = location.isDeliveryAllowed();
        this.addrLine1=location.getAddrLine1();
        this.addrLine2=location.getAddrLine2();
        this.addrLine3=location.getAddrLine3();
        this.city=location.getCity();
        this.state=location.getState();
        this.country=location.getCountry();
        this.pincode = location.getPincode();
    }
}
