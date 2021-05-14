package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Person;
import com.utn.TPfinal.persistence.PersonDao;
import org.springframework.stereotype.Service;

@Service

public class PersonService {

    PersonDao personDao;
    public void add(Person person) {
     personDao.save(person);
    }
}
