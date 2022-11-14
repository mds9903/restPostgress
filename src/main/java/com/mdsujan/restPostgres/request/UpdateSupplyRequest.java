package com.mdsujan.restPostgres.request;

import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSupplyRequest {
    Long supplyId;
    Long itemId;
    Long locationId;
    Long supplyQty;
    AllowedSupplyTypes supplyType;
}
