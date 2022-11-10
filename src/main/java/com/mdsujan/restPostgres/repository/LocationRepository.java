package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Modifying
    @Transactional
    @Query("Delete from Location where id = :id")
    void deleteById(Long id);
}
