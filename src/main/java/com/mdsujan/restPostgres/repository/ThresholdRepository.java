package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Threshold;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThresholdRepository extends MongoRepository<Threshold, Long> {

//    List<Threshold> findByItemAndLocation(Long itemId, Long locationId);

    Threshold findByItemIdAndLocationId(Long itemId, Long locationId);

//    @Modifying
//    @Transactional
//    @Query("Update Threshold set minThreshold = :minThreshold, maxThreshold = :maxThreshold WHERE item = :item AND
//    location = :location")
//    Integer UpdateThresholdsByItemAndLocation(Item item, Location location, Long minThreshold, Long maxThreshold);

    List<Threshold> findByItemId(Long itemId);
}
