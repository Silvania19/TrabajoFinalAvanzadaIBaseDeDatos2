package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Address;
import com.utn.TPfinal.exception.AddressException;
import com.utn.TPfinal.service.AddressService;
import com.utn.TPfinal.util.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    public ResponseEntity newAddress(@RequestBody Address address) throws AddressException {
        Address newAddress = addressService.newAddress(address);

        URI location = EntityURLBuilder.buildURL("address", newAddress.getIdAddress());
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @PutMapping("/{id}")
    public ResponseEntity updateAddress(@PathVariable Integer id, @RequestBody Address address) throws AddressException {
        Address newAddress = addressService.updateAddress(id, address);
        URI location = EntityURLBuilder.buildURL("address", newAddress.getIdAddress());

        return ResponseEntity.ok(location);
    }

    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAddress(@PathVariable Integer id)
    {
        addressService.deleteAddress(id);

        return ResponseEntity.ok().build();
        //return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        // devuelvo no content porque:El request se ha procesado correctamente, pero no devuelve ningún contenido.
    }

    // devolver response
    /*@PutMapping("/{id}/client/{idClient}")
    public void addClientToAddress(@PathVariable Integer id, @PathVariable Integer idClient){
        addressService.addClientToAddress(id, idClient);
    }*/
}