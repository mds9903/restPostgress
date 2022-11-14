package com.mdsujan.restPostgres.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemRequest {

    Long itemId;

    String itemDesc;

    String category;

    String itemType;

    String status;

    Double price;

    Boolean pickupAllowed;

    Boolean shippingAllowed;

    Boolean deliveryAllowed;
}
