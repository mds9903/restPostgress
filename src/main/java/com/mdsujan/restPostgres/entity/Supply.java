package com.mdsujan.restPostgres.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
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
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // use GenerationType.IDENTITY for "serial" id in psql table
//    @Column(name = "supply_id")
    @JsonIgnore
    Long supplyId;

//    @Column(name = "supply_type")
//    @Enumerated(EnumType.STRING)
    @NotNull
    AllowedSupplyTypes supplyType;

//    @Column(name = "qty")
    @NotNull
    Long supplyQty;

//    @OneToOne // relation with items table via the fk itemId
//    @JoinColumn(name = "item_id")
    @NotNull
    Item item;

//    @OneToOne // relation with locations table via the fk locationId
//    @JoinColumn(name = "location_id")
    @NotNull
    Location location;

    public Supply(CreateSupplyRequest createSupplyRequest) {
        this.supplyType = createSupplyRequest.getSupplyType();
        this.supplyQty = createSupplyRequest.getSupplyQty();
    }
}
