package com.mdsujan.restPostgres.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DemandDetails {
    Long plannedQty;
    Long hardPromisedQty;
    public DemandDetails(Long plannedQty, Long hardPromisedQty) {
        this.plannedQty = plannedQty;
        this.hardPromisedQty = hardPromisedQty;
    }
}
