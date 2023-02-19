package com.mdsujan.restPostgres.entity;

import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
//@Table(name = "demand",schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Demand {
    @Id
    Long demandId;
    AllowedDemandTypes demandType;
    Long demandQty;
    Long itemId;
    Long locationId;

    public Demand(CreateDemandRequest createDemandRequest) {
        this.demandQty = createDemandRequest.getDemandQty();
        this.demandType = createDemandRequest.getDemandType();
        this.demandId = createDemandRequest.getDemandId();
        this.itemId = createDemandRequest.getItemId();
        this.locationId = createDemandRequest.getLocationId();
    }
}
