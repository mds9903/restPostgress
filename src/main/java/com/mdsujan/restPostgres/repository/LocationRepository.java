package com.mdsujan.restPostgres.repository;

import com.mdsujan.restPostgres.entity.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//import javax.transaction.Transactional;
@Repository
public interface LocationRepository extends MongoRepository<Location, Long> {

}
