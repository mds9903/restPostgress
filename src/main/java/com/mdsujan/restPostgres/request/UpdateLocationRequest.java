package com.mdsujan.restPostgres.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateLocationRequest {
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
}
