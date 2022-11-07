package com.mdsujan.restPostgres.response;

import com.mdsujan.restPostgres.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
    private Long id;

    @Setter
    private String desc;
    @Setter
    private String category;
    @Setter
    private String type;
    @Setter
    private String status;
    @Setter
    private Double price;
    @Setter
    private Boolean pickupAllowed;
    @Setter
    private Boolean shippingAllowed;
    @Setter
    private Boolean deliveryAllowed;

    public ItemResponse(Item item) {
        this.id = item.getItemId();
        this.desc=item.getItemDesc();
        this.category=item.getCategory();
        this.type=item.getType();
        this.status=item.getStatus();
        this.price=item.getPrice();
        this.deliveryAllowed=item.getDeliveryAllowed();
        this.shippingAllowed=item.getShippingAllowed();
        this.pickupAllowed=item.getPickupAllowed();

    }

    @Override
    public String toString() {
        return "ItemResponse{" + "id='" + id + '\'' + ", desc='" + desc + '\'' + ", category='" + category + '\'' + ", type='" + type + '\'' + ", status='" + status + '\'' + ", price=" + price + ", pickupAllowed=" + pickupAllowed + ", shippingAllowed=" + shippingAllowed + ", deliveryAllowed=" + deliveryAllowed + '}';
    }
}
