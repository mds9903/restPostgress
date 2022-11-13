package com.mdsujan.restPostgres.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThresholdDetailsResponse {
    Long itemId;
    Long locationId;
    ThresholdDetails thresholdDetails;

    public ThresholdDetailsResponse(Long itemId, Long locationId, ThresholdDetails thresholdDetails){
        this.itemId = itemId;
        this.locationId = locationId;
        this.thresholdDetails = thresholdDetails;
    }
}
