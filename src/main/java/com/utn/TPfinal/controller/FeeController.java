package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/fee")
public class FeeController {
    @Autowired
    FeeService feeService;

    @PostMapping
    public ResponseEntity addFee(@RequestBody Fee fee) throws FeeException {
        Fee newFee= feeService.add(fee);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newFee.getIdFee())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity updateFee(@PathVariable Integer id, @RequestBody Fee fee) throws FeeException {
        Fee newFee= feeService.updateFee(id, fee);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newFee.getIdFee())
                .toUri();
        return ResponseEntity.ok(location);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteFee(@PathVariable Integer id)
    {
         feeService.deleteFee(id);
         return ResponseEntity.status(HttpStatus.OK).build();
    }


}
