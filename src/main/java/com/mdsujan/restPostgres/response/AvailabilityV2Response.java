package com.mdsujan.restPostgres.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AvailabilityV2Response {
    /*

     *
     * Sample Response:
     * {
     *   itemId: 123,
     *   locationId: 456,
     *   availabilityQty: 5,
     *   stockLevel: Red
     * }
     *
     * */

    Long itemId;

    Long locationId;

    Long availabilityQty;

    String stockLevel;

    public AvailabilityV2Response(Long itemId, Long locationId, Long availableQty, String stockLevel) {
        this.itemId = itemId;
        this.locationId = locationId;
        this.availabilityQty = availableQty;
        this.stockLevel = stockLevel;
    }
}
