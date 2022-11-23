package com.mdsujan.restPostgres.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "supply", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supply_id")
    @NotNull
    @JsonIgnore
    Long supplyId;

    @Column(name = "supply_type")
    @Enumerated(EnumType.STRING)
    @NotNull
    AllowedSupplyTypes supplyType;

    @Column(name = "qty")
    @NotNull
    Long supplyQty;

    @OneToOne
    @JoinColumn(name = "item_id")
    @NotNull
    Item item;

    @OneToOne
    @JoinColumn(name = "location_id")
    @NotNull
    Location location;

    public Supply(CreateSupplyRequest createSupplyRequest) {
        this.supplyType = createSupplyRequest.getSupplyType();
        this.supplyQty = createSupplyRequest.getSupplyQty();
    }
}
