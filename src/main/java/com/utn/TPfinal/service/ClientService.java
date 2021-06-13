package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.exception.NotFoundException;
import com.utn.TPfinal.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

    @Service

    public class ClientService {

        @Autowired
        ClientRepository clientRepository;

        public void add(Client user) {
            clientRepository.save(user);
        }

        public Client findByNameAndPassword(String name, String password) {
            return clientRepository.findByNameAndPassword(name, password);
        }

        public List<Client> tenMoreConsumers(Date beginDate, Date endDate) {
            List<Client> userList= clientRepository.tenMoreConsumers(beginDate, endDate);
            return  userList;
        }
        public Client getByID(Integer id) {
            return clientRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("No se encontro"));
        }

    }


