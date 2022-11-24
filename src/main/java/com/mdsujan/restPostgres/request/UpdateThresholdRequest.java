package com.mdsujan.restPostgres.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateThresholdRequest {
    @NotNull
    Long itemId;
    @NotNull
    Long locationId;
    @NotNull
    Long maxThreshold;
    @NotNull
    Long minThreshold;
}
