package com.mdsujan.restPostgres.request;

import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CreateSupplyRequest {
    @NotNull Long supplyId;
    @NotNull AllowedSupplyTypes supplyType;
    @NotNull Long supplyQty;
    @NotNull Long itemId;
    @NotNull Long locationId;
}
