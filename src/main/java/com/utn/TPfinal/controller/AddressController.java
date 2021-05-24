package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Address;
import com.utn.TPfinal.domain.Person;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping
    public ResponseEntity newAddress(@RequestBody Address address) throws FeeException {
        Address newAddress = addressService.newAddress(address);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAddress.getId_address())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}/client/{idClient}")
    public void addClientToAddress(@PathVariable Integer id, @PathVariable Integer idClient){
        addressService.addClientToAddress(id, idClient);
    }
}

/*

[17:28, 19/5/2021] Silvania: @PostMapping(consumes = "application/json")
    public ResponseEntity newCountry(@RequestBody Country country) throws CountryExistsException {
        Country newCountry = countryService.newCountry(country);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCountry.getCode())
                .toUri();
        return ResponseEntity.created(location).build();
    }

[17:29, 19/5/2021] Silvania: public Country newCountry(Country country) throws CountryExistsException {
        if (!countryRepository.existsById(country.getCode())) {
            return countryRepository.save(country);
        } else {
            throw new CountryExistsException();
        }
    }


*/