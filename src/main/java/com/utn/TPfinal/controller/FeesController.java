package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/fee")
public class FeesController {
    @Autowired
    FeeService feeService;

    @PostMapping
    public ResponseEntity addFee(@RequestBody Fee fee) throws FeeException {
        Fee newFee= feeService.add(fee);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newFee.getId_fee())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity updateFee(@PathVariable Integer id, @PathVariable Fee fee) throws FeeException {
        Fee newFee= feeService.updateFee(fee);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newFee.getId_fee())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
