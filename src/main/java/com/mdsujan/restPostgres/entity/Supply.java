package com.mdsujan.restPostgres.entity;

import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "supply", schema = "public")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supply_id")
    Long id;

    @Column(name = "supply_type")
    @Enumerated(EnumType.STRING)
    AllowedSupplyTypes supplyType;

    @Column(name = "qty")
    Long qty;

    @OneToOne
    @JoinColumn(name = "item_id")
    Item item;

    @OneToOne
    @JoinColumn(name = "location_id")
    Location location;

    public Supply(CreateSupplyRequest createSupplyRequest) {
        this.id = createSupplyRequest.getSupplyId();
//        this.supplyType = AllowedSupplyTypes.valueOf(createSupplyRequest.getSupplyType());
        this.supplyType = createSupplyRequest.getSupplyType();
        this.qty = createSupplyRequest.getSupplyQty();
    }
}
