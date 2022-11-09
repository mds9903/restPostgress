package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.repository.DemandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandService {
    @Autowired
    DemandRepository demandRepository;
    public List<Demand> getAll() {
        return demandRepository.findAll();
    }

    public Demand getDemandById(Long demandId) {
        return demandRepository.findById(demandId).get();
    }
}
