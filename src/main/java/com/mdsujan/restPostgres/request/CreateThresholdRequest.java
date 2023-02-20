package com.mdsujan.restPostgres.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
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
