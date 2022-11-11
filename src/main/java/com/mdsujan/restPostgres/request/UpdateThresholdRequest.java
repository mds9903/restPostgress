package com.mdsujan.restPostgres.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateThresholdRequest {
    Long thresholdId;
    Long itemId;
    Long locationId;
    Long maxThreshold;
    Long minThreshold;
}
