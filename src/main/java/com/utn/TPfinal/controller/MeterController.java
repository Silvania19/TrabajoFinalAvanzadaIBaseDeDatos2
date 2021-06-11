package com.utn.TPfinal.controller;


import com.utn.TPfinal.domain.Meter;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.service.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/meter")
public class MeterController {
    @Autowired
    MeterService meterService;

    @PostMapping
    public ResponseEntity addMeter(@RequestBody Meter meter) throws FeeException {
        Meter newMeter= meterService.add(meter);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newMeter.getSerialNumber())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idMeter}")
    public void updateMeter (@PathVariable String serialNumber, @RequestBody Meter meter){
        meterService.updateMeter(serialNumber, meter);
    }

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity deleteMeter(@PathVariable String serialNumber)
    {
         meterService.deleteMeter(serialNumber);
        return ResponseEntity.ok().build();
    }
}
