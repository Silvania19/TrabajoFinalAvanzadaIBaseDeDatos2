package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.domain.dto.ConsuptionDto;
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

@RestController
@RequestMapping("/apiClient")
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

    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @GetMapping("/unpaid")
    public ResponseEntity<List<Bill>> billsNotPay(Authentication authentication) {
        Client client = modelMapper.map(authentication.getPrincipal(), Client.class);
        if (client != null) {
            List<Bill> bills = billController.getBillsNotPay(client.getId());
            HttpStatus httpStatus = bills.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
            return ResponseEntity.status(httpStatus).body(bills);

        } else {
            return ResponseEntity.notFound().build();
        }
    }


}