package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    List<Supply> findByItemIdAndLocationId(Long itemId, Long locationId);
//    List<Supply> getSupplyByLocationId();
}
