package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
