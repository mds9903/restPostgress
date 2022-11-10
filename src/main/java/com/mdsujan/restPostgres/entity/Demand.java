package com.mdsujan.restPostgres.entity;

import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "demand",schema = "public")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Demand {
    @Id
    @Column(name = "demand_id")
    Long demandId;

    @Column(name = "demand_type")
    @Enumerated(EnumType.STRING)
    AllowedDemandTypes demandType;

    @Column(name = "qty")
    Long demandQty;

    @OneToOne
    @JoinColumn(name = "item_id")
    Item item;

    @OneToOne
    @JoinColumn(name = "location_id")
    Location location;

    public Demand(CreateDemandRequest createDemandRequest) {
        this.demandId = createDemandRequest.getDemandId();
        this.demandQty = createDemandRequest.getDemandQty();
        this.demandType = createDemandRequest.getDemandType();
    }
}
