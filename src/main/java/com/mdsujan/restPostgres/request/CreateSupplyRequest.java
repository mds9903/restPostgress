package com.mdsujan.restPostgres.request;

import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSupplyRequest {
//    @NotNull
//    Long supplyId;
    @NotNull
    Long itemId;
    @NotNull
    Long locationId;
    @NotNull
    AllowedSupplyTypes supplyType;
    @NotNull
    Long supplyQty;
}
