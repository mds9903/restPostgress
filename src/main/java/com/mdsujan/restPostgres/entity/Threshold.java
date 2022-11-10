package com.mdsujan.restPostgres.entity;

import com.mdsujan.restPostgres.request.CreateThresholdRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Threshold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "threshold_id", nullable = false)
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
        this.thresholdId = createThresholdRequest.getThresholdId();
        this.minThreshold = createThresholdRequest.getMinThreshold();
        this.maxThreshold = createThresholdRequest.getMaxThreshold();
    }
}
