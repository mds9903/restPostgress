package com.mdsujan.restPostgres.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "locations")
@Getter
@Setter
//@Table(name = "location", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Id
    @NotNull
    Long locationId;

    @NotNull String locationDesc;

    @NotNull String type;

    @NotNull Boolean pickupAllowed;

    @NotNull Boolean shippingAllowed;

    @NotNull Boolean deliveryAllowed;

    @NotNull String addrLine1;

    @NotNull String addrLine2;

    @NotNull String addrLine3;

    @NotNull String city;

    @NotNull String state;

    @NotNull String country;

    @NotNull String pincode;


//    public Location(long locationId,
//                    String locationDesc,
//                    String type,
//                    boolean pickupAllowed,
//                    boolean shippingAllowed,
//                    boolean deliveryAllowed,
//                    String city,
//                    String state,
//                    String country,
//                    String pincode) {
//        this.locationId = locationId;
//        this.locationDesc = locationDesc;
//        this.type = type;
//        this.pickupAllowed = pickupAllowed;
//        this.shippingAllowed = shippingAllowed;
//        this.deliveryAllowed = deliveryAllowed;
//        this.city = city;
//        this.state = state;
//        this.country = country;
//        this.pincode = pincode;
//    }
}
