package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.User;
import com.utn.TPfinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service

public class UserService {

    @Autowired
    UserRepository userRepository;

    public void add(User user) {
     userRepository.save(user);
    }

    public User getByID(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }
    public User login(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }


}
