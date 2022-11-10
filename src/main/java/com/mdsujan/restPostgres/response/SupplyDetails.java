package com.mdsujan.restPostgres.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplyDetails {


    Long onhandQty;
    Long intransitQty;

    public SupplyDetails(Long onhandQty, Long intransitQty) {
        this.onhandQty = onhandQty;
        this.intransitQty = intransitQty;
    }
}
