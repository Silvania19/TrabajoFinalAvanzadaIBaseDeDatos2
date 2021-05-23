package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Person;
import com.utn.TPfinal.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

  /*  @PostMapping
    public void addPerson(@RequestBody Person person) {
         personService.add(person);
    }*/

}
