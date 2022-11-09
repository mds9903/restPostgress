package com.mdsujan.restPostgres.response;


import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplyResponse {

    Long id;

    AllowedSupplyTypes type;

    Integer qty;

    Long itemId;

    Long locationId;

    public SupplyResponse(Supply supply) {
        this.id = supply.getId();
        this.type = supply.getSupplyType();
        this.qty = supply.getQty();

        this.itemId = supply.getItem().getItemId();
        this.locationId = supply.getLocation().getLocationId();
    }
}
