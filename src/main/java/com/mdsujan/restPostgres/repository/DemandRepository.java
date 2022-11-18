package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DemandRepository extends JpaRepository<Demand, Long> {
    List<Demand> findByItemItemIdAndLocationLocationId(Long itemId, Long locationId);

    List<Demand> findByItemItemId(Long itemId);

    Collection<Demand> findByLocationLocationId(Long locationId);
}
