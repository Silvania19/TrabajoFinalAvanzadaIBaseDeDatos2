package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.repository.MeasuringRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.utn.TPfinal.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MeasuringServiceTest {
    MeasuringRepository measuringRepository;
    MeasuringService measuringService;
     @BeforeEach
    public void setUp(){
        this.measuringRepository= mock(MeasuringRepository.class);
        measuringService= new MeasuringService(measuringRepository);
    }
    @Test
    public void addMeasuringHappyPath(){
         //given
        when(measuringRepository.save(newMeasuring())).thenReturn(newMeasuring());
        //when
        Measuring measuring= measuringService.add(newMeasuring());
        //then
        assertEquals(newMeasuring(), measuring);
    }

    /* @SneakyThrows
    @Test*/
   /* public  void consumptionHappyPath(){
         when(measuringRepository.consumption(anyInt(), any(), any())).thenReturn(any());
         Consumption consumption= measuringRepository.consumption(1, aDate1(), aDate2());
         verify(measuringRepository, times(1)).consumption(1, aDate1(), aDate2());

    }*/
  /*  @SneakyThrows
    @Test
    public void measuringRangeDateByAddress(){
        Pageable pageable = PageRequest.of(1, 10);
        when(measuringRepository.getMeasuringByAddressAndRangeDate(anyInt(), any(), any(),eq(pageable)))
                       .thenReturn(aPageMeasuring());

            Page<Measuring> pageMeasuring= measuringService.measuringRangeDateByAddress(1, aDate1(), aDate2(), pageable);
            assertEquals(pageMeasuring, aPageMeasuring());
    }*/
}
