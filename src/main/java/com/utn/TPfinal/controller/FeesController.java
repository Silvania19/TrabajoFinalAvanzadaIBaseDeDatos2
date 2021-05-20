package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class FeesController {
    @Autowired
    FeeService feeService;
    @PostMapping
    public ResponseEntity addFee(@RequestBody Fee fee) {
        Fee newFee= feeService.add(fee);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newFee.getId_fees())
                .toUri();
        return ResponseEntity.created(location).build();
    }



}
