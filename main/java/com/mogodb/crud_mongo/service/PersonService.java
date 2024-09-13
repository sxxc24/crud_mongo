package com.mogodb.crud_mongo.service;

import com.mogodb.crud_mongo.collections.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {
    String SavePerson(Person person);
    List<Person> getAll();
    List<Person> getUserByCityName(String address);
    List<Person> findByMongoTemplate(String city);
    List<Person> usingPageable(String city, Integer pgno, Integer size);
    Page<Person> usingPageablePlusTemplate(String city, Pageable pageable);
}
