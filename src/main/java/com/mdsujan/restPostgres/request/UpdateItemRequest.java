package com.mdsujan.restPostgres.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemRequest {

    @NotNull
    Long itemId;

    @NotNull
    String itemDesc;

    @NotNull
    String category;

    @NotNull
    String itemType;

    @NotNull
    String status;

    @NotNull
    Double price;

    @NotNull
    Boolean pickupAllowed;

    @NotNull
    Boolean shippingAllowed;

    @NotNull
    Boolean deliveryAllowed;
}
