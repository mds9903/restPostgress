package com.mdsujan.restPostgres.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateItemRequest {

    Long id;

    String desc;

    String category;

    String type;

    String status;

    Double price;

    Boolean pickupAllowed;

    Boolean shippingAllowed;

    Boolean deliveryAllowed;
}
