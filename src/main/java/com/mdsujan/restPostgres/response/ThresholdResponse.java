package com.mdsujan.restPostgres.response;

import com.mdsujan.restPostgres.entity.Threshold;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThresholdResponse {
    Long thresholdId;
    Long itemId;
    Long locationId;
    Long maxThreshold;
    Long minThreshold;

    public ThresholdResponse(Threshold threshold) {
        this.thresholdId = threshold.getThresholdId();
        this.itemId = threshold.getItemId();
        this.locationId = threshold.getLocationId();
        this.minThreshold = threshold.getMinThreshold();
        this.maxThreshold = threshold.getMaxThreshold();
    }
}
