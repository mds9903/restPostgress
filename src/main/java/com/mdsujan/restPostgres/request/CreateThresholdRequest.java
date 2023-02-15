package com.mdsujan.restPostgres.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateThresholdRequest {
    @NotNull
    Long thresholdId;
    @NotNull
    Long itemId;
    @NotNull
    Long locationId;
    @NotNull
    Long minThreshold;
    @NotNull
    Long maxThreshold;
}
