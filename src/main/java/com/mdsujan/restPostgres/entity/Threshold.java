package com.mdsujan.restPostgres.entity;

import com.mdsujan.restPostgres.request.CreateThresholdRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Threshold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "threshold_id")
    Long thresholdId;

    @OneToOne
    @JoinColumn(name = "item_id")
    Item item;

    @OneToOne
    @JoinColumn(name = "location_id")
    Location location;

    Long minThreshold;

    Long maxThreshold;

    public Threshold(CreateThresholdRequest createThresholdRequest) {
//        this.thresholdId = createThresholdRequest.getThresholdId();
        this.minThreshold = createThresholdRequest.getMinThreshold();
        this.maxThreshold = createThresholdRequest.getMaxThreshold();
    }
}
