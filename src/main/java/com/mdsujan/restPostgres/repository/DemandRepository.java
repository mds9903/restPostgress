package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Demand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandRepository extends JpaRepository<Demand, Long> {
}
