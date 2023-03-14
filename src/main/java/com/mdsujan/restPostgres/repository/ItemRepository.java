package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

//@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Modifying
    @Transactional
    @Query("Delete from Item where itemId = :id")
    void deleteById(Long id);

//    List<Item> findByItemDescContains(String pattern);

    // case sensitive
//    List<Item> findByItemDescStartsWith(String pattern);

    // case sensitive
    @Query("SELECT i FROM Item i WHERE LOWER(itemDesc) LIKE CONCAT(?1, '%')")
    List<Item> searchByItemDesc(String pattern);
}
