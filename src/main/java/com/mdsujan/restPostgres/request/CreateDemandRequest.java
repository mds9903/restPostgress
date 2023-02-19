package com.mdsujan.restPostgres.request;

import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CreateDemandRequest {
    Long demandId;
    Long itemId;
    Long locationId;
    AllowedDemandTypes demandType;
    Long demandQty;
}
