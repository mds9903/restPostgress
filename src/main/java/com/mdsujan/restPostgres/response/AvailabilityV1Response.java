package com.mdsujan.restPostgres.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class AvailabilityV1Response {
    Long itemId;

//    Long locationId;
    String locationId; // locationId can also be a string so keeping it as string itself

    Long availableQty;
    public AvailabilityV1Response(Long itemId, Long locationId, Long availableQty) {
        this.itemId = itemId;
        this.locationId = locationId.toString();
        this.availableQty = availableQty;
    }

    public AvailabilityV1Response(Long itemId, String locationId, Long availableQty) {
        this.itemId = itemId;
        this.locationId = locationId;
        this.availableQty = availableQty;
    }

}
