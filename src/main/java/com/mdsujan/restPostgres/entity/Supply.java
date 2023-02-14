package com.mdsujan.restPostgres.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "supply", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Supply {

    @Id
    @JsonIgnore
    Long supplyId;

    @NotNull
    AllowedSupplyTypes supplyType;

//    @Column(name = "qty")
    @NotNull
    Long supplyQty;

//    @OneToOne // relation with items table via the fk itemId
//    @JoinColumn(name = "item_id")
//    @NotNull
//    Item item;
    @NotNull
    Long itemId;

//    @OneToOne // relation with locations table via the fk locationId
//    @JoinColumn(name = "location_id")
//    @NotNull
//    Location location;

    @NotNull
    Long locationId;

    public Supply(CreateSupplyRequest createSupplyRequest) {
        this.supplyId = createSupplyRequest.getSupplyId();
        this.supplyType = createSupplyRequest.getSupplyType();
        this.supplyQty = createSupplyRequest.getSupplyQty();
    }
}
