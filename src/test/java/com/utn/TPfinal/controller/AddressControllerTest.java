package com.utn.TPfinal.controller;

import com.utn.TPfinal.service.AddressService;
import com.utn.TPfinal.util.EntityURLBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.utn.TPfinal.utils.Constants.NUMBER_OF_ID_ONE;
import static com.utn.TPfinal.utils.Constants.ONE_INVOCATION;
import static com.utn.TPfinal.utils.TestUtils.aAddress;
import static com.utn.TPfinal.utils.TestUtils.aFee;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class AddressControllerTest {
    @Mock
    AddressService addressService;

    AddressController addressController;
    final String ENTITY_NAME = "address";

    @BeforeEach
    void setUp() {
        initMocks(this);
        addressController = new AddressController(addressService);
    }

    @Test
    void newAddressHappypathTest() {

        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(addressService.newAddress(aAddress())).thenReturn(aAddress());

        //when
        ResponseEntity response = addressController.newAddress(aAddress());

        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(EntityURLBuilder.buildURL(ENTITY_NAME, aAddress().getIdAddress()),
                response.getHeaders().getLocation());
    }

    @Test
    void updateAddressHapptyPathTest() {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(addressService.updateAddress(NUMBER_OF_ID_ONE, aAddress())).thenReturn(aAddress());

        //when
        ResponseEntity response = addressController.updateAddress(NUMBER_OF_ID_ONE, aAddress());

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(EntityURLBuilder.buildURL(ENTITY_NAME, aAddress().getIdAddress()),
                response.getBody());

    }

    @Test
    void deleteAddressHapptyPathTest() {
        ResponseEntity response=  addressController.deleteAddress(aAddress().getIdAddress());
        verify(addressService, times(ONE_INVOCATION)).deleteAddress(aAddress().getIdAddress());
    }
}