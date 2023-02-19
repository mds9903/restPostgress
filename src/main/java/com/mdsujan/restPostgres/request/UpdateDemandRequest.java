package com.mdsujan.restPostgres.request;

import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDemandRequest {
    @NotNull
    Long itemId;
    @NotNull
    Long locationId;
    @NotNull
    AllowedDemandTypes demandType;
    @NotNull
    Long demandQty;
}
