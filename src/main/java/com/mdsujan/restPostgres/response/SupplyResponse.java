package com.mdsujan.restPostgres.response;


import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplyResponse {
    AllowedSupplyTypes type;
    Long supplyQty;
    Long itemId;
    Long locationId;

    public SupplyResponse(Supply supply) {
        this.type = supply.getSupplyType();
        this.supplyQty = supply.getSupplyQty();
        this.itemId = supply.getItem().getItemId();
        this.locationId = supply.getLocation().getLocationId();
    }
}
