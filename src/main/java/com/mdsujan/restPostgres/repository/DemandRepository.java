package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandRepository extends JpaRepository<Demand, Long> {
    List<Demand> findByItemIdAndLocationId(Long itemId, Long locationId);
}
