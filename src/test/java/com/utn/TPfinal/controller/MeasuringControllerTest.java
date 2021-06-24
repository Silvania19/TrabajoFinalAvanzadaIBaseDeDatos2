package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.service.MeasuringService;
import com.utn.TPfinal.service.MeterService;
import com.utn.TPfinal.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.Date;

import static com.utn.TPfinal.utils.Constants.PAGE_ONE;
import static com.utn.TPfinal.utils.Constants.SIZE_TEN;
import static com.utn.TPfinal.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeasuringControllerTest {
    MeasuringService measuringService;
    ModelMapper modelMapper;
    MeterService meterService;
    MeasuringController measuringController;
    @BeforeEach
    public void setUp(){
        this.measuringService= mock(MeasuringService.class);
        this.modelMapper= mock(ModelMapper.class);
        this.meterService= mock(MeterService.class);
        this.measuringController= new MeasuringController(measuringService, modelMapper, meterService);
    }
    @Test
    public void addMeasuring(){

        Measuring measuring= newMeasuring();
        measuring.setMeter(aMeter());
        when(modelMapper.map(aMeasuringDto(), Measuring.class)).thenReturn(newMeasuring());
        when(meterService.findBySerialNumberAndPasswordMeter(aMeasuringDto().getSerialNumber(), aMeasuringDto().getPassword()))
        .thenReturn(aMeter());
        when(measuringService.add(measuring)).thenReturn(measuring);

        Measuring measuring1= measuringController.addMeasuring(aMeasuringDto());

        assertEquals(measuring, measuring1);

    }
    @Test
    public void measuringsRangeDateAndAddressOK(){
        Pageable pageable = PageRequest.of(PAGE_ONE, SIZE_TEN);
        Page<Measuring> mockedPage = mock(Page.class);
        Date beginDate = mock(Date.class);
        Date endDate = mock(Date.class);
        when(measuringService.measuringRangeDateByAddress(aAddress().getIdAddress(), beginDate, endDate, pageable))
                .thenReturn(aPageMeasuring());
        when(mockedPage.getContent()).thenReturn(aListMeasuring());

        ResponseEntity responseEntity= measuringController.measuringsRangeDateAndAddress(aAddress().getIdAddress(),
                beginDate, endDate, pageable);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), aListMeasuring());
    }
}
