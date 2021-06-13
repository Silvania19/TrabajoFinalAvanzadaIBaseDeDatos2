package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/apiClient")
public class ClientController {

    ClientService clientService;
    @Autowired
    public ClientController(ClientService clientService){
        this.clientService= clientService;
    }


}
