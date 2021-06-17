package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.domain.dto.ConsuptionDto;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class ClientController {

    ClientService clientService;
    ModelMapper modelMapper;
    BillController billController;

    @Autowired
    public ClientController(ClientService clientService, ModelMapper modelMapper, BillController billController) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
        this.billController = billController;
    }

    // agregar responseentity
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @GetMapping("backoffice/clients")
    public List<UserDto> moreConsumersOfDateRange(
            @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate){

        List<UserDto> userList = clientService.tenMoreConsumers(beginDate, endDate).
                stream().map(o -> modelMapper.map(o, UserDto.class)).collect(Collectors.toList());
        return userList;
    }


}