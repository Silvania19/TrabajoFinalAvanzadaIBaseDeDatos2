package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.domain.Meter;
import com.utn.TPfinal.domain.dto.ConsuptionDto;
import com.utn.TPfinal.domain.dto.MeasuringDto;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.projecciones.Consumption;
import com.utn.TPfinal.repository.MeasuringRepository;
import com.utn.TPfinal.service.MeasuringService;
import com.utn.TPfinal.service.MeterService;
import com.utn.TPfinal.util.EntityURLBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import org.springframework.data.domain.Pageable;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
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

    /**apiClient**/

    /* client 5) Consulta de mediciones por rango de fechas */

    @PreAuthorize(value = "hasAnyAuthority('CLIENT')")
    @GetMapping("client/measuringByRangeOfDates")
    public List<Bill> getMeasuringsByRangeOfDates(Authentication authentication,
                                                  @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
                                                  @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate){
        return null;
    }

    /*4) Consulta de consumo por rango de fechas (el usuario va a ingresar un rango
         de fechas y quiere saber cuánto consumió en ese periodo en Kwh y dinero) */

    @PreAuthorize(value = "hasAnyAuthority('CLIENT')")
    @GetMapping("apiClient/consumption")
    public ResponseEntity consumptionRangeDateKwMoney(Authentication authentication,
                                                      @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
                                                      @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate) {
        UserDto userDto=(UserDto) authentication.getPrincipal();
        Consumption consumption= measuringService.consumption(userDto.getId(), beginDate, endDate);
        if(consumption.getPriceTotal()!= null && consumption.getTotalKwh()!= null){
           return ResponseEntity.ok(consumption);
        }else {
          return ResponseEntity.noContent().build();
        }

    }

    /**BACKOFFICE**/


    /*6) Consulta de mediciones de un domicilio por rango de fechas*/
    @PreAuthorize(value = "hasAnyAuthority('BACKOFFICE')")
    @GetMapping("backoffice/address/{idAddress}")
    public ResponseEntity measuringsRangeDateAndAddress(@PathVariable Integer idAddress,
                                                       @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date beginDate,
                                                       @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate,
                                                       Pageable pageable){

        Page pageMeasuring= measuringService.measuringRangeDateByAddress(idAddress, beginDate, endDate, pageable);

        return response(pageMeasuring);
    }
    private ResponseEntity response(Page page) {
        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }
}
