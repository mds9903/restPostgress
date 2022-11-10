package com.mdsujan.restPostgres.response;

import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
        this.itemId= demand.getItem().getItemId();
        this.locationId=demand.getLocation().getLocationId();
    }
}
