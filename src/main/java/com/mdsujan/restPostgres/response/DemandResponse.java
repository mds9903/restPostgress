package com.mdsujan.restPostgres.response;

import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandResponse {
    Long demandId;

    AllowedDemandTypes demandType;

    Long demandQty;

    Long itemId;

    Long locationId;

    public DemandResponse(Demand demand) {
        this.demandId = demand.getDemandId();
        this.demandType = demand.getDemandType();
        this.demandQty = demand.getDemandQty();
        this.itemId = demand.getItemId();
        this.locationId = demand.getLocationId();
    }
}
