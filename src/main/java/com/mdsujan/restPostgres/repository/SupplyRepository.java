package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Supply;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
@Repository
public interface SupplyRepository extends MongoRepository<Supply, Long> {
    List<Supply> findByItemItemIdAndLocationLocationId(Long itemId, Long locationId);

    List<Supply> findByItemItemId(Long itemId);

    Collection<Supply> findByLocationLocationId(Long locationId);

    List<Supply> findBySupplyTypeAndLocationLocationId(Long supplyType, Long locationId);
//    List<Supply> getSupplyByLocationId();
}
