package com.mdsujan.restPostgres.request;

import lombok.*;

@Data

public class CreateItemRequest {

    private Long id;

    private String desc;

    private String category;

    private String type;

    private String status;

    private Double price;

    private Boolean pickupAllowed;

    private Boolean shippingAllowed;

    private Boolean deliveryAllowed;
}
