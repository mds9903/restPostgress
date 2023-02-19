package com.mdsujan.restPostgres.request;

import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateSupplyRequest {
    @NotNull
    AllowedSupplyTypes supplyType;

    @NotNull
    Long supplyQty;

    @NotNull
    Long locationId;

    @NotNull
    Long itemId;

}
