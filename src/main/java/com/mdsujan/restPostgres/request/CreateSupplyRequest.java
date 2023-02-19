package com.mdsujan.restPostgres.request;

import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSupplyRequest {
    @NotNull Long supplyId;
    @NotNull AllowedSupplyTypes supplyType;
    @NotNull Long supplyQty;
    @NotNull Long itemId;
    @NotNull Long locationId;
}
