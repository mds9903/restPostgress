package com.mdsujan.restPostgres.entity;

import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "demand",schema = "public")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Demand {
    @Id
    @Column(name = "demand_id")
    Long id;

    @Column(name = "demand_type")
    AllowedDemandTypes type;

    @Column(name = "quantity")
    Integer qty;

    @OneToOne
    @JoinColumn(name = "item_id")
    Item item;

    @OneToOne
    @JoinColumn(name = "location_id")
    Location location;
}
