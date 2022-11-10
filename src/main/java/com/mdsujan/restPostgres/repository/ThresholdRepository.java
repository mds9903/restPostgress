package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Threshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThresholdRepository extends JpaRepository<Threshold, Long> {

//    List<Threshold> findByItemAndLocation(Long itemId, Long locationId);

    List<Threshold> findByItemItemIdAndLocationLocationId(Long itemId, Long locationId);
}
