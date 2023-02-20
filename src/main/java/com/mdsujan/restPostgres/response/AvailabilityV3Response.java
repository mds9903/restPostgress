package com.mdsujan.restPostgres.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AvailabilityV3Response {
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

    public AvailabilityV3Response(Long itemId, Long locationId, Long availableQty, String stockLevel) {
        this.itemId = itemId;
        this.locationId = locationId;
        this.availabilityQty = availableQty;
        this.stockLevel = stockLevel;
    }
}
