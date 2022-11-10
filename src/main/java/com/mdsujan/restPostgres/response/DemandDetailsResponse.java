package com.mdsujan.restPostgres.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DemandDetailsResponse {
    Long itemId;
    Long locationId;
    DemandDetails demandDetails;

    public DemandDetailsResponse(Long itemId, Long locationId, DemandDetails demandDetails) {
        this.itemId = itemId;
        this.locationId = locationId;
        this.demandDetails = demandDetails;
    }
}
