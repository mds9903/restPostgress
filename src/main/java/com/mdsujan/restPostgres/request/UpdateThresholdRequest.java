package com.mdsujan.restPostgres.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
