package com.mdsujan.restPostgres.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.management.Query;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateItemRequest {

    @NotNull
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
