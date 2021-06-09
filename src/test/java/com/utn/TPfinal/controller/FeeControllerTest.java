package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.service.FeeService;
import com.utn.TPfinal.util.EntityURLBuilder;
import com.utn.TPfinal.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.utn.TPfinal.utils.TestUtils.aFee;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FeeControllerTest {
    @Mock
    FeeService feeService;
    FeeController feeController;
    final String entity= "fee";
    @BeforeEach
    public void setUp() {
        initMocks(this);
        feeController= new FeeController(feeService);
    }
    @Test
    public void addFeeHappyPath(){
       //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Fee fee= aFee();
        when(feeService.add(fee)).thenReturn(fee);
       //when
         ResponseEntity response = feeController.addFee(fee);
       //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(EntityURLBuilder.buildURL(entity, fee.getIdFee()),
                      response.getHeaders().getLocation());
    }
    @Test
    public  void updateFeeHappyPath(){
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Fee fee= aFee();
        Integer id=1;
        when(feeService.updateFee(id, fee)).thenReturn(fee);
        //when
        ResponseEntity response= feeController.updateFee(id, fee);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(EntityURLBuilder.buildURL(entity, fee.getIdFee()),
                     response.getBody());
    }


}
