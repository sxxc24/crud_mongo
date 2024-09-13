package com.mogodb.crud_mongo.service;

import com.mogodb.crud_mongo.collections.Person;
import com.mogodb.crud_mongo.repo.personRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImp implements PersonService {

    //dependencies
    private personRepo repo;
    @Autowired
    public void setPersonRepo(personRepo repo) {
        this.repo = repo;
    }

    private MongoTemplate mongoTemplate;
    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    //dependencies

    @Override
    public String SavePerson(Person person) {
        return repo.save(person).getId();
    }

    @Override
    public List<Person> getAll() {
        return repo.findAllWithoutAddress();
    }

    @Override
    public List<Person> getUserByCityName(String address) {
        return repo.findUserByCity(address);
    }

    @Override
    public List<Person> findByMongoTemplate(String city) {
        Query query = new Query();

        query
                .addCriteria(Criteria.where("personAddress.personCity").is(city))
                .fields()
                .exclude("personAddress")
                .exclude("_id");

        return mongoTemplate.find(query,Person.class);
    }

    @Override
    public List<Person> usingPageable(String city, Integer pgno, Integer size) {
        Pageable page= PageRequest.of(pgno, size);
        return repo.findByCityName(city, page).getContent();
    }

    @Override
    public Page<Person> usingPageablePlusTemplate(String city, Pageable pageable) {
        Query query = new Query();
        query
                .addCriteria(Criteria.where("personAddress.personCity").is(city))
                .fields()
                .exclude("personAddress");

        query.with(pageable);

        long count=mongoTemplate.count(query,Person.class);

        List<Person> user=mongoTemplate.find(query,Person.class);

        return new PageImpl<Person>(user,pageable,count);
    }


}
