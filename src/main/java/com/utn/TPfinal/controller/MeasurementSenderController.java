package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.domain.Meter;
import com.utn.TPfinal.domain.dto.MeasuringDto;
import com.utn.TPfinal.repository.MeasuringRepository;
import com.utn.TPfinal.service.MeasuringService;
import com.utn.TPfinal.service.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MeasurementSenderController {

    MeasuringController measuringController;
    MeterService meterService;
    @Autowired
    public MeasurementSenderController(MeasuringController measuringController, MeterService meterService){
        this.measuringController=measuringController;
        this.meterService=meterService;
    }

    @PostMapping("measurements")
    public void addMeasurement(@RequestBody MeasuringDto measuringDto){
        System.out.printf("hola");
          if (measuringDto !=null){
              Meter meter = meterService.findBySerialNumberAndPasswordMeter(measuringDto.getSerialNumber(), measuringDto.getPassword());
               if(meter != null ){
                   measuringController.addMeasuring(measuringDto);


               }
          }
    }
}
