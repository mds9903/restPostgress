package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Item;
import org.springframework.context.annotation.Bean;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

//import javax.transaction.Transactional;

//@Repository
public interface ItemRepository extends MongoRepository<Item, Long> {

//    @Modifying
//    @Transactional
    @Query("Delete from Item where itemId = :id")
    void deleteById(Long id);


}
