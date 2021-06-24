package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Meter;
import com.utn.TPfinal.exception.MeterNotExistsException;
import com.utn.TPfinal.exception.MeterExistsException;
import com.utn.TPfinal.exception.MeterWithMeasuringsException;
import com.utn.TPfinal.repository.MeterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.utn.TPfinal.utils.TestUtils.aMeter;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MeterServiceTest {
    MeterService meterService;
    MeterRepository meterRepository;
    @BeforeEach
    public void setUp(){
        this.meterRepository= mock(MeterRepository.class);
        this.meterService= new MeterService(meterRepository);
    }
    @Test
    public void addOk(){
        //GIVEN
        try {
            when(meterRepository.findBySerialNumber(aMeter().getSerialNumber())).thenReturn(null);
            when(meterRepository.save(aMeter())).thenReturn(aMeter());
            // THEN
            Meter meter1= meterService.add(aMeter());
            //WHEN
            assertEquals(meter1, aMeter());
        }catch (MeterExistsException me){

        }

    }
    @Test
    public void addMeterExistExeption(){
        try {
            //GIVEN
            when(meterRepository.findBySerialNumber(aMeter().getSerialNumber())).thenReturn(aMeter());
            when(meterRepository.save(aMeter())).thenReturn(aMeter());
            // THEN
            Meter meter1= meterService.add(aMeter());
            //WHEN
            assertEquals(meter1, aMeter());
        }catch (MeterExistsException me){

        }
    }

    @Test
    public  void updateMeterOK(){
        //given
        when(meterRepository.findBySerialNumber(aMeter().getSerialNumber())).thenReturn(aMeter());
        when(meterRepository.save(aMeter())).thenReturn(aMeter());
        //when
        Meter meter=meterService.updateMeter(aMeter().getSerialNumber(), aMeter());
        //then
        assertEquals(meter, aMeter());
    }

    @Test
    public  void updateMeterException(){
        //given
        when(meterRepository.findBySerialNumber(aMeter().getSerialNumber())).thenReturn(null);
        when(meterRepository.save(aMeter())).thenReturn(aMeter());

        assertThrows(MeterNotExistsException.class, () -> {
            meterService.updateMeter("1234", aMeter());
        });
    }

    @Test
    public void deleteMeterOk(){
        when(meterRepository.findBySerialNumber(aMeter().getSerialNumber())).thenReturn(aMeter());
        try {
            meterService.deleteMeter(aMeter().getSerialNumber());
            verify(meterRepository, times(1)).deleteBySerialNumber(aMeter().getSerialNumber());
        } catch (MeterWithMeasuringsException e) {
            e.printStackTrace();
        }
    }
   @Test
    public void deleteMeterException(){
        when(meterRepository.findBySerialNumber(aMeter().getSerialNumber())).thenReturn(null);
        try {
            meterService.deleteMeter(aMeter().getSerialNumber());
            verify(meterRepository, times(1)).deleteBySerialNumber(aMeter().getSerialNumber());
        } catch (MeterWithMeasuringsException e) {

        }
    }
    @Test
    public void findBySerialNumberAndPasswordMeter(){
        when(meterRepository.findBySerialNumberAndPasswordMeter(aMeter().getSerialNumber(),
                aMeter().getPasswordMeter())).thenReturn(aMeter());
        Meter meter = meterService.findBySerialNumberAndPasswordMeter(aMeter().getSerialNumber(),
                aMeter().getPasswordMeter());
        assertEquals(meter, aMeter());
    }

}
