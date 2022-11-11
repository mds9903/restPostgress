package com.mdsujan.restPostgres.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateThresholdRequest {
//  Long thresholdId;
//  this is an auto generated id
//  so user won't be able to give this id;
//  as it will change and be saved based on the sequence of insertions
    Long itemId;
    Long locationId;
    Long minThreshold;
    Long maxThreshold;
}
