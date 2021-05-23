package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Person;
import com.utn.TPfinal.persistence.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service

public class PersonService {

    @Autowired
    PersonDao personDao;

    public void add(Person person) {
     personDao.save(person);
    }

    public Person getByID(Integer id) {
        return personDao.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }


}
