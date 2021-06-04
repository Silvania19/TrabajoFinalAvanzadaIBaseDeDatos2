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
                .buildAndExpand(newMeter.getIdMeter())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /*@PutMapping("/{idMeter}/address/{idAddress}/model/{idModel}/fee/{idFee}")

    public void addAddressModelFeeToMeter(@PathVariable Integer idMeter, @PathVariable Integer idAddress, @PathVariable Integer idModel, @PathVariable Integer idFee){
        meterService.addAddressModelFeeToMeter(idMeter,idAddress, idModel, idFee);
    }*/
    @PutMapping("/{idMeter}")
    public void updateMeter (@PathVariable Integer idMeter, @RequestBody Meter meter){
        meterService.updateMeter(idMeter, meter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMeter(@PathVariable Integer id)
    {
         meterService.deleteMeter(id);
        return ResponseEntity.ok().build();
    }
}
