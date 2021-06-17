package com.utn.TPfinal.controller;


import com.utn.TPfinal.domain.Meter;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.service.MeterService;
import com.utn.TPfinal.util.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/meter")
public class MeterController {

    MeterService meterService;
    @Autowired
    public MeterController(MeterService meterService){
        this.meterService=meterService;
    }
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @PostMapping
    public ResponseEntity addMeter(@RequestBody Meter meter) throws FeeException {
        Meter newMeter= meterService.add(meter);
        URI location= EntityURLBuilder.buildURLString("meter", newMeter.getSerialNumber());
        return ResponseEntity.created(location).build();
    }
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @PutMapping("/{serialNumber}")
    public ResponseEntity updateMeter (@PathVariable String serialNumber, @RequestBody Meter meter){
        Meter meter1 =meterService.updateMeter(serialNumber, meter);
        URI location = EntityURLBuilder.buildURLString("meter", meter1.getSerialNumber());
        return  ResponseEntity.ok(location);
    }

    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @DeleteMapping("/{serialNumber}")
    public ResponseEntity deleteMeter(@PathVariable String serialNumber)
    {
        try {
            meterService.deleteMeter(serialNumber);
            return ResponseEntity.ok().build();
        }
       catch (Exception e){
            return ResponseEntity.notFound().build();
       }

    }
}
