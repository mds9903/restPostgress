package com.mdsujan.restPostgres.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplyDetailsResponse {
    /*
    * A sample SupplyDetailsResponse should look like this
    * {
    *   itemId: 123,
    *   locationId: 123,
    *   supplyDetails: {
    *       onhand: 10,
    *       intransit: 20
    *   }
    * }
    * */

    Long itemId;
    Long locationId;
    SupplyDetails supplyDetails;

}
