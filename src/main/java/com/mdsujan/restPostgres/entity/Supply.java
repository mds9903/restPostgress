package com.mdsujan.restPostgres.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Supply {
    @Id
    @JsonIgnore
    Long supplyId;
    @NotNull AllowedSupplyTypes supplyType;
    @NotNull Long supplyQty;
    @NotNull Long itemId;

    @NotNull Long locationId;

    public Supply(CreateSupplyRequest createSupplyRequest) {
        this.supplyId = createSupplyRequest.getSupplyId();
        this.supplyType = createSupplyRequest.getSupplyType();
        this.supplyQty = createSupplyRequest.getSupplyQty();
        this.itemId = createSupplyRequest.getItemId();
        this.locationId = createSupplyRequest.getLocationId();
    }
}
