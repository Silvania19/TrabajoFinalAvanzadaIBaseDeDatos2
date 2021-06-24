package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Meter;
import com.utn.TPfinal.exception.MeterExistsException;
import com.utn.TPfinal.service.MeterService;
import com.utn.TPfinal.util.EntityURLBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.utn.TPfinal.utils.TestUtils.aMeter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class MeterControllerTest {
    MeterService meterService;
    MeterController meterController;
    final String ENTITY_NAME = "meter";

    @BeforeEach
    public void setUp() {
        this.meterService = Mockito.mock(MeterService.class);
        meterController = new MeterController(meterService);
    }

    @Test
    public void addMeterHappyPath() {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
       try {
           Meter meter = aMeter();
           when(meterService.add(meter)).thenReturn(meter);
           //when
           ResponseEntity response = meterController.addMeter(meter);
           //then
           assertEquals(HttpStatus.CREATED, response.getStatusCode());
           assertEquals(EntityURLBuilder.buildURLString(ENTITY_NAME, meter.getSerialNumber()),
                   response.getHeaders().getLocation());
       }catch (MeterExistsException meterExitsException){
            fail();
       }

    }

    @Test
    public void updateMeterHappyPath() {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Meter meter = aMeter();
        when(meterService.updateMeter(meter.getSerialNumber(), meter)).thenReturn(meter);

        //when
        ResponseEntity response = meterController.updateMeter(aMeter().getSerialNumber(), meter);
        //then

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(EntityURLBuilder.buildURLString(ENTITY_NAME, meter.getSerialNumber()),
                response.getBody());
    }

   /* @Test
    public void deleteMeter() {

        ResponseEntity response= meterController.deleteMeter(aMeter().getSerialNumber());
        verify(meterService, times(1)).deleteMeter(aMeter().getSerialNumber());

    }*/
}