package com.mdsujan.restPostgres.request;

import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDemandRequest {
    Long demandId;
    Long itemId;
    Long locationId;
    Long qty;
    AllowedDemandTypes demandType;
}
