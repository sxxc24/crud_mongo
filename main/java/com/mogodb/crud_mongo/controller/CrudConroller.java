package com.mogodb.crud_mongo.controller;

import com.mogodb.crud_mongo.collections.Person;
import com.mogodb.crud_mongo.repo.personRepo;
import com.mogodb.crud_mongo.service.PersonServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CrudConroller {


    PersonServiceImp personServiceImp;
    @Autowired
    public void setPersonServiceImp(PersonServiceImp personServiceImp) {
        this.personServiceImp = personServiceImp;
    }

    private personRepo repo;
    @Autowired
    public void setPersonRepo(personRepo repo) {
        this.repo = repo;
    }

    @PostMapping("/adduser")
    public String addUser(@RequestBody Person person) {
        return personServiceImp.SavePerson(person);
    }

    @GetMapping("/fetchuser")
    public List<Person> getUser() {
        return personServiceImp.getAll();
    }
    @GetMapping("/fetchuserbycity/{cityname}")
    public List<Person> getUserByCity(@PathVariable("cityname") String cityname) {
        return personServiceImp.getUserByCityName(cityname);
    }

    @GetMapping("/mongotemplatecity/{cityname}")
    public List<Person> getMongoTemplate(@PathVariable("cityname") String cityname) {
        return personServiceImp.findByMongoTemplate(cityname);
    }

    @GetMapping("/pagable")
    public List<Person> getPagable(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "3") Integer size,@RequestParam String city) {
        return personServiceImp.usingPageable(city, page, size); // this will not display the page properties only list of contents will be displayed
    }

    @GetMapping("/mongotemplatewithpagable")
    public List<Person> getPageableWithMongoTemplate(@RequestParam(defaultValue = "0") Integer pgno, @RequestParam(defaultValue = "3") Integer size,@RequestParam String city) {
        Pageable page = PageRequest.of(pgno,size);
        return personServiceImp.usingPageablePlusTemplate(city,page).getContent();
    }

}
