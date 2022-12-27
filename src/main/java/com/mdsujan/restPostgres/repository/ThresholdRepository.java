package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Threshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ThresholdRepository extends JpaRepository<Threshold, Long> {

//    List<Threshold> findByItemAndLocation(Long itemId, Long locationId);

    Threshold findByItemItemIdAndLocationLocationId(Long itemId, Long locationId);

    @Modifying
    @Transactional
    @Query("Update Threshold set minThreshold = :minThreshold, maxThreshold = :maxThreshold WHERE item = :item AND location = :location")
    Integer UpdateThresholdsByItemAndLocation(Item item, Location location, Long minThreshold, Long maxThreshold);

    List<Threshold> findByItemItemId(Long itemId);
}
