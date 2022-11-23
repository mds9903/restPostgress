package com.mdsujan.restPostgres.request;

import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSupplyRequest {
    Long itemId;
    Long locationId;
    AllowedSupplyTypes supplyType;
    Long supplyQty;
}
