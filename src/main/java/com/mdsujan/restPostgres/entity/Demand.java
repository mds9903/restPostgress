package com.mdsujan.restPostgres.entity;

import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "demand",schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Demand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "demand_id")
    Long demandId;

    @Column(name = "demand_type")
    @Enumerated(EnumType.STRING)
    AllowedDemandTypes demandType;

    @Column(name = "qty")
    Long demandQty;

    @OneToOne // relation with items table via the fk itemId
    @JoinColumn(name = "item_id")
    Item item;

    @OneToOne // relation with items table via the fk locationId
    @JoinColumn(name = "location_id")
    Location location;

    public Demand(CreateDemandRequest createDemandRequest) {
        this.demandQty = createDemandRequest.getDemandQty();
        this.demandType = createDemandRequest.getDemandType();
    }
}
