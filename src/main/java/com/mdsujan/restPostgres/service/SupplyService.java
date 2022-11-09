package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyService {

    @Autowired
    SupplyRepository supplyRepository;

    public List<Supply> getAllSupplies(){
        return supplyRepository.findAll();
    }

    public Supply getSupplyById(Long supplyId) {
        return supplyRepository.findById(supplyId).get();
    }
}
