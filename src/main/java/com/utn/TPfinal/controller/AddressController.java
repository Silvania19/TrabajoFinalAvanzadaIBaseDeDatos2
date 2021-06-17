package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Address;
import com.utn.TPfinal.exception.AddressException;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.service.AddressService;
import com.utn.TPfinal.util.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @PostMapping
    public ResponseEntity newAddress(@RequestBody Address address) throws AddressException {
        System.out.println("holaaaaaaaaaaaaa");
        Address newAddress = addressService.newAddress(address);

        URI location = EntityURLBuilder.buildURL("address", newAddress.getIdAddress());
        return ResponseEntity.created(location).build();
    }

    // TODO: 24/5/2021 como devuelvo la url correcta ?, por ahora retorna "http://localhost:8080/address/1/1"
    //  y deberia retornar  http://localhost:8080/address/1
    @PutMapping("/{id}")
    public ResponseEntity updateAddress(@PathVariable Integer id, @RequestBody Address address) throws AddressException {
        Address newAddress = addressService.updateAddress(id, address);
        URI location = EntityURLBuilder.buildURL("address", id);


        /*URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAddress.getIdAddress())
                .toUri();*/
        return ResponseEntity.ok(location);
    }

    @DeleteMapping("/{id}")
    public void deleteFee(@PathVariable Integer id)
    {
        addressService.deleteFee(id);
    }

    @PutMapping("/{id}/client/{idClient}")
    public void addClientToAddress(@PathVariable Integer id, @PathVariable Integer idClient){
        addressService.addClientToAddress(id, idClient);
    }
}