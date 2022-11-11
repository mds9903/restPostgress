package com.mdsujan.restPostgres.response;

import com.mdsujan.restPostgres.entity.Item;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
//    @JsonIgnore // do not show id for the item
    Long itemId;
    String itemDesc;
    String category;
    String itemType;
    String status;
    Double price;
    Boolean pickupAllowed;
    Boolean shippingAllowed;
    Boolean deliveryAllowed;

    public ItemResponse(Item item) {
        this.itemId = item.getItemId();
        this.itemDesc = item.getItemDesc();
        this.category = item.getCategory();
        this.itemType = item.getType();
        this.status = item.getStatus();
        this.price = item.getPrice();
        this.deliveryAllowed = item.getDeliveryAllowed();
        this.shippingAllowed = item.getShippingAllowed();
        this.pickupAllowed = item.getPickupAllowed();

    }

}
