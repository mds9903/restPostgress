package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
