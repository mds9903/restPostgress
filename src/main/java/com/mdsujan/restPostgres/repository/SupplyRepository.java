package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Supply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SupplyRepository extends MongoRepository<Supply, Long> {
    List<Supply> findByItemIdAndLocationId(Long itemId, Long locationId);

    List<Supply> findByItemId(Long itemId);

    Collection<Supply> findByLocationId(Long locationId);

}
