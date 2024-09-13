package com.mogodb.crud_mongo.repo;

import com.mogodb.crud_mongo.collections.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface personRepo extends MongoRepository<Person,String> {

    @Query(value = "{}",fields = "{address : 0}")
    List<Person> findAllWithoutAddress();

    @Query(value="{'personAddress.personCity': ?0 }" , fields="{address: 0}")
    List<Person> findUserByCity(String address);

    @Query(value="{'personAddress.personCity': ?0 }",fields="{address: 0}")
    Page<Person> findByCityName(String address, Pageable pageable);
}
