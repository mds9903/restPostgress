package com.mdsujan.restPostgres.response;


import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SupplyResponse {
    Long supplyId;
    AllowedSupplyTypes type;
    Long supplyQty;
    Long itemId;
    Long locationId;

    public SupplyResponse(Supply supply) {
        this.supplyId = supply.getSupplyId();
        this.type = supply.getSupplyType();
        this.supplyQty = supply.getSupplyQty();
        this.itemId = supply.getItemId();
        this.locationId = supply.getLocationId();
    }
}
