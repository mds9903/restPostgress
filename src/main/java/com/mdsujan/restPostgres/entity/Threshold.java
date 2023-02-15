package com.mdsujan.restPostgres.entity;

import com.mdsujan.restPostgres.request.CreateThresholdRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//import org.hibernate.Hibernate;
//
//import javax.persistence.*;
//import java.util.Objects;

@Document(collection = "thresholds")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Threshold {
    @Id
    Long thresholdId;
    Long itemId;
    Long locationId;
    Long minThreshold;
    Long maxThreshold;

    public Threshold(CreateThresholdRequest createThresholdRequest) {
        this.minThreshold = createThresholdRequest.getMinThreshold();
        this.maxThreshold = createThresholdRequest.getMaxThreshold();
        this.thresholdId = createThresholdRequest.getThresholdId();
        this.itemId = createThresholdRequest.getItemId();
        this.locationId = createThresholdRequest.getLocationId();
    }
}
