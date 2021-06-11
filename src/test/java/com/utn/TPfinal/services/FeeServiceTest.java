package com.utn.TPfinal.services;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.exception.FeeException;

import com.utn.TPfinal.repository.FeeRepository;
import com.utn.TPfinal.service.FeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static com.utn.TPfinal.utils.TestUtils.aFee;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class FeeServiceTest {

      FeeRepository feeRepository;
      FeeException feeException;
      FeeService feeService;
      @BeforeEach
      public void setUp(){
          feeRepository = mock(FeeRepository.class);
          feeService= new FeeService(feeRepository);
      }
      @Test
      public void addHappyPath(){
             //given

              Fee fee= aFee();
              when(feeRepository.existsById(fee.getIdFee())).thenReturn(false);
              when(feeRepository.save(fee)).thenReturn(fee);

              //when
              Fee feeR= feeService.add(fee);
              //then
              assertEquals(feeR.getTypeFee(), fee.getTypeFee());
      }
    @Test
    public void addSadPath(){
        //given

        Fee fee= aFee();
        when(feeRepository.existsById(fee.getIdFee())).thenReturn(true);

        //then
        assertThrows(FeeException.class, ()-> {
            feeService.add(fee);
            });
     }
/*
     @Test
    public void updateFeeHappyPath(){
          //given
         when(feeRepository.findById(1)).thenReturn(Optional.of(aFee()));
         Fee nfee= aFee();
         nfee.setTypeFee("updFee");
         try {
             feeService.updateFee(aFee().getIdFee(), nfee);
             verify(feeRepository, times(1)).existsById(aFee().getIdFee());
             verify(feeService, times(1)).getByID(aFee().getIdFee());
             verify(feeRepository, times(1)).save(nfee);
         }catch (FeeException no){
            fail();
         }
        /* when(feeRepository.existsById(id)).thenReturn(true);
         when(feeService.getByID(nfee.getIdFee())).thenReturn(nfee);
         //when
         Fee feeN= feeRepository.save(nfee);
         //then
         assertEquals(feeN.getTypeFee(), nfee.getTypeFee());
     }*/


    /* @Test
    public void deleteFeeHappyTest(){

     }*/


}
