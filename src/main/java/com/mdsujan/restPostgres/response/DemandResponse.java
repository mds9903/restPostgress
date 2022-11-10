package com.mdsujan.restPostgres.response;

import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DemandResponse {
    Long demandId;

    AllowedDemandTypes demandType;

    Long demandQty;

    Long itemId;

    Long locationId;

    public DemandResponse(Demand demand) {
        this.demandId=demand.getDemandId();
        this.demandType = demand.getDemandType();
        this.demandQty=demand.getDemandQty();
//        this.itemId= demand.getItem().getItemId();
        this.itemId= demand.getItem().getId();
//        this.locationId=demand.getLocation().getLocationId();
        this.locationId=demand.getLocation().getId();
    }
}
