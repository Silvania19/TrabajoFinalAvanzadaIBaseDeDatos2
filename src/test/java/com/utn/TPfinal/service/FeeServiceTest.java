package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.exception.AddressException;
import com.utn.TPfinal.exception.FeeException;

import com.utn.TPfinal.repository.FeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static com.utn.TPfinal.utils.Constants.ONE_INVOCATION;
import static com.utn.TPfinal.utils.TestUtils.aFee;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class FeeServiceTest {

    FeeRepository feeRepository;
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
    public void updateFeeHappyPath(){
         try {
             when(feeRepository.findById(aFee().getIdFee())).thenReturn(Optional.of(aFee()));
             Fee nfee= aFee();
             nfee.setTypeFee("updFee");

             when(feeRepository.save(nfee)).thenReturn(nfee);
             //when
             Fee feeN= feeService.updateFee(aFee().getIdFee(), nfee);
             //then
             assertEquals(feeN.getTypeFee(), nfee.getTypeFee());
         }catch (FeeException e){
             fail();
         }

     }

    @Test
    public void updateFeeExeption() {
        when(feeRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(FeeException.class, () -> {
            feeService.updateFee(1, aFee());
        });
    }

    @Test
    public void deleteFeeHappyTest(){
         try {
             Fee fee= aFee();
             when(feeRepository.findById(fee.getIdFee())).thenReturn(Optional.of(fee));
             // then
             feeService.deleteFee(fee.getIdFee());

             verify(feeRepository, times(ONE_INVOCATION)).delete(fee);
         }catch (FeeException e){
             fail();
         }

     }

    @Test
    public void deleteFeeExceptionTest(){
            Fee fee= aFee();
            when(feeRepository.findById(2)).thenReturn(Optional.of(fee));
            // then

            assertThrows(FeeException.class, () -> {
                feeService.deleteFee(1);
            });

    }


}
