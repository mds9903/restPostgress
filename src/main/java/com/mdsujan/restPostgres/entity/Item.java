package com.mdsujan.restPostgres.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    @Id
    @NotNull
    private Long itemId;

    @NotNull
    private String itemDesc;

    @NotNull
    private String category;

    @NotNull
    private String itemType;

    @NotNull
    private String status;

    @NotNull
    private Double price;

    @NotNull
    private Boolean pickupAllowed;

    @NotNull
    private Boolean shippingAllowed;

    @NotNull
    private Boolean deliveryAllowed;

}
