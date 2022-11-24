package com.mdsujan.restPostgres.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateThresholdRequest {
    @NotNull
    Long itemId;
    @NotNull
    Long locationId;
    @NotNull
    Long minThreshold;
    @NotNull
    Long maxThreshold;
}
