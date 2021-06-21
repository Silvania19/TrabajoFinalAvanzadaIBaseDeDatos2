package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.exception.AddressException;
import com.utn.TPfinal.exception.FeeException;

import com.utn.TPfinal.repository.FeeRepository;
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
    /*@Test
    public void addSadPath(){
        //given

        Fee fee= aFee();
        when(feeRepository.existsById()).thenReturn(true);

        //then
        assertThrows(FeeException.class, ()-> {
            feeService.add(fee);
        });
    }
*/
     @Test
    public void updateFeeHappyPath(){
          //given
         /* when(feeRepository.existsById(1)).thenReturn(true);
         when(feeService.getByID(aFee().getIdFee())).thenReturn(aFee());
         Fee nfee= aFee();
         nfee.setTypeFee("updFee");
         try {
             feeService.updateFee(aFee().getIdFee(), nfee);
             verify(feeRepository, times(1)).existsById(aFee().getIdFee());
             verify(feeService, times(1)).getByID(aFee().getIdFee());
             verify(feeRepository, times(1)).save(nfee);
         }catch (FeeException no){
            fail();
         }*/
         when(feeRepository.findById(aFee().getIdFee())).thenReturn(Optional.of(aFee()));
         Fee nfee= aFee();
         nfee.setTypeFee("updFee");

         when(feeRepository.save(nfee)).thenReturn(nfee);
         //when
         Fee feeN= feeService.updateFee(aFee().getIdFee(), nfee);
         //then
         assertEquals(feeN.getTypeFee(), nfee.getTypeFee());
     }
    @Test
    public void updateFeeExceptionTest(){

        try {
            when(feeRepository.findById(2)).thenReturn(Optional.of(aFee()));
            Fee nfee= aFee();
            nfee.setTypeFee("updFee");
            when(feeRepository.save(nfee)).thenReturn(nfee);
            //when
            Fee feeN= feeService.updateFee(aFee().getIdFee(), nfee);

        }catch (FeeException fe){

        }
    }


    @Test
    public void deleteFeeHappyTest(){
        Fee fee= aFee();
        when(feeRepository.findById(fee.getIdFee())).thenReturn(Optional.of(fee));
        // then
        feeService.deleteFee(fee.getIdFee());

        verify(feeRepository, times(1)).delete(fee);
     }

    @Test
    public void deleteFeeExceptionTest(){
        Fee fee= aFee();
        try {
            when(feeRepository.findById(2)).thenReturn(Optional.of(fee));
            // then
            feeService.deleteFee(fee.getIdFee());

            verify(feeRepository, times(1)).delete(fee);

        }catch (FeeException e){

        }

    }


}
