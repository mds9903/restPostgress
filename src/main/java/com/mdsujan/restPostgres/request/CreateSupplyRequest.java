package com.mdsujan.restPostgres.request;

import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSupplyRequest {
    Long supplyId;
    
    Long itemId;
//    Long itemId;
//    String itemDesc;
//    String category;
//    String type;
//    String status;
//    Double price;
//    Boolean itemPickupAllowed;
//    Boolean itemShippingAllowed;
//    Boolean itemDeliveryAllowed;
    
    Long locationId;
//    Long locationId;
//    String locationDesc;
//    String locationType;
//    Boolean locationPickupAllowed;
//    Boolean locationShippingAllowed;
//    Boolean locationDeliveryAllowed;
//    String addrLine1;
//    String addrLine2;
//    String addrLine3;
//    String city;
//    String state;
//    String country;
//    String pincode;

    AllowedSupplyTypes supplyType;
//    String supplyType;
    Long supplyQty;
}
