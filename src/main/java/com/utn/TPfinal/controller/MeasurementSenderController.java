package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Meter;
import com.utn.TPfinal.domain.dto.MeasuringDto;
import com.utn.TPfinal.service.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/measurements")
public class MeasurementSenderController {

    MeasuringController measuringController;
    MeterService meterService;
    @Autowired
    public MeasurementSenderController(MeasuringController measuringController, MeterService meterService){
        this.measuringController=measuringController;
        this.meterService=meterService;
    }
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @PostMapping
    public void addMeasurement(@RequestBody MeasuringDto measuringDto){
          if (measuringDto !=null){
              Meter meter = meterService.findBySerialNumberAndPasswordMeter(measuringDto.getSerialNumber(), measuringDto.getPassword());
               if(meter != null ){
                  measuringController.addMeasuring(measuringDto);
               }
          }
    }
}
