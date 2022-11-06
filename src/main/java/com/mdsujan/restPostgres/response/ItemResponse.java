package com.mdsujan.restPostgres.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
    private String id;

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

    @Override
    public String toString() {
        return "ItemResponse{" + "id='" + id + '\'' + ", desc='" + desc + '\'' + ", category='" + category + '\'' + ", type='" + type + '\'' + ", status='" + status + '\'' + ", price=" + price + ", pickupAllowed=" + pickupAllowed + ", shippingAllowed=" + shippingAllowed + ", deliveryAllowed=" + deliveryAllowed + '}';
    }
}
