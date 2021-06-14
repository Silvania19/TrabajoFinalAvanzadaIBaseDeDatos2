package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.domain.Meter;
import com.utn.TPfinal.domain.dto.MeasuringDto;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.repository.MeasuringRepository;
import com.utn.TPfinal.service.MeasuringService;
import com.utn.TPfinal.service.MeterService;
import com.utn.TPfinal.util.EntityURLBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/measuring")
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

    /* client 5) Consulta de mediciones por rango de fechas */

    @PreAuthorize(value = "hasAnyAuthority('CLIENT')")
    @GetMapping("client/measuringByRangeOfDates")
    public List<Bill> getMeasuringsByRangeOfDates(Authentication authentication,
                                                  @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
                                                  @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate){
        return null;
    }
}
