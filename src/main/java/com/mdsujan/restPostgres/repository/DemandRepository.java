package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Demand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DemandRepository extends MongoRepository<Demand, Long> {
    List<Demand> findByItemIdAndLocationId(Long itemId, Long locationId);

    List<Demand> findByItemId(Long itemId);

    Collection<Demand> findByLocationId(Long locationId);
}
