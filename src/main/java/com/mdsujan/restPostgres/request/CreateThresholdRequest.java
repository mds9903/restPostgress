package com.mdsujan.restPostgres.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateThresholdRequest {
    Long thresholdId;
    Long itemId;
    Long locationId;
    Long minThreshold;
    Long maxThreshold;
}
