package com.mdsujan.restPostgres.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateItemRequest {

    private Long itemId;

    private String itemDesc;

    private String category;

    private String itemType;

    private String status;

    private Double price;

    private Boolean pickupAllowed;

    private Boolean shippingAllowed;

    private Boolean deliveryAllowed;
}
