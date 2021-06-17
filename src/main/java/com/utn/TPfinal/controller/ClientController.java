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
    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @GetMapping("apiClient/unpaid")
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
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @GetMapping("backoffice/clients")
    public List<UserDto> moreConsumersOfDateRange(
            @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate){
       /*  return  modelMapper.map();
        lientService.getAll(pageable).map(o -> modelMapper.map(o,ClientDto.class));

        return  modelMapper.map( userList,Class<List<UserDto>>);
        ///  return residenceRepository.findAll(pageable).map(residence -> modelMapper.map(residence, ResidenceDto.class));
    */
        return userList;
    }

}