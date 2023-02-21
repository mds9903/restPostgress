package com.mdsujan.restPostgres.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ThresholdDetails {
    Long minThreshold;
    Long maxThreshold;

    public ThresholdDetails(Long minThreshold, Long maxThreshold) {
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }
}
