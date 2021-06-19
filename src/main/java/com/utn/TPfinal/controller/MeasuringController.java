package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.domain.Meter;
import com.utn.TPfinal.domain.dto.MeasuringDto;
import com.utn.TPfinal.service.MeasuringService;
import com.utn.TPfinal.service.MeterService;
import com.utn.TPfinal.util.ResponseEntityList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/measurings")
public class MeasuringController {

    MeasuringService measuringService;
    ModelMapper modelMapper;
    MeterService meterService;
    @Autowired
    public MeasuringController(MeasuringService measuringService, ModelMapper modelMapper, MeterService meterService){
        this.measuringService=measuringService;
        this.modelMapper=modelMapper;
        this.meterService= meterService;
    }

    public Measuring addMeasuring(@RequestBody MeasuringDto measuringDto){
        Measuring measuring = modelMapper.map(measuringDto, Measuring.class);
        Meter meter = meterService.findBySerialNumberAndPasswordMeter(measuringDto.getSerialNumber(), measuringDto.getPassword());
        measuring.setMeter(meter);
        Measuring newMeasuring= measuringService.add(measuring);
       // URI location = EntityURLBuilder.buildURL("fee", newMeasuring.getIdMeasuring());
        return newMeasuring;
    }

    /**BACKOFFICE**/

    /*6) Consulta de mediciones de un domicilio por rango de fechas*/
    @PreAuthorize(value = "hasAnyAuthority('BACKOFFICE')")
    @GetMapping("/{idMeasuring}")
    public ResponseEntity measuringsRangeDateAndAddress(@PathVariable Integer idMeasuring,
                                                       @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
                                                       @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate,
                                                       Pageable pageable){

        Page pageMeasuring = measuringService.measuringRangeDateByAddress(idMeasuring, beginDate, endDate, pageable);

        return ResponseEntityList.response(pageMeasuring);
    }

}
