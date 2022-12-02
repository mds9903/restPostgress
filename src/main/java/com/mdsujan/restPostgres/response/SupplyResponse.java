package com.mdsujan.restPostgres.response;


import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.AccessType;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
        this.itemId = supply.getItem().getItemId();
        this.locationId = supply.getLocation().getLocationId();
    }
}
