package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.domain.Meter;
import com.utn.TPfinal.domain.dto.MeasuringDto;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.repository.MeasuringRepository;
import com.utn.TPfinal.service.MeasuringService;
import com.utn.TPfinal.util.EntityURLBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import javax.swing.text.html.parser.Entity;
import java.net.URI;

@RestController
@RequestMapping("/measuring")
public class MeasuringController {

    MeasuringService measuringService;
    ModelMapper modelMapper;
    @Autowired
    public MeasuringController(MeasuringService measuringService, ModelMapper modelMapper){
        this.measuringService=measuringService;
        this.modelMapper=modelMapper;
    }
    @PreAuthorize(value = "hasAuthority('BACKOFFICE')")
    @PostMapping
    public ResponseEntity addMeasuring(@RequestBody MeasuringDto measuringDto){
        Measuring measuring = modelMapper.map(measuringDto, Measuring.class);
        Measuring newMeasuring= measuringService.add(measuring);
        URI location = EntityURLBuilder.buildURL("fee", newMeasuring.getIdMeasuring());
        return ResponseEntity.created(location).build();
    }
}
