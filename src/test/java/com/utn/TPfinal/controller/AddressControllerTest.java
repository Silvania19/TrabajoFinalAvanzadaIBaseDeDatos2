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

import static com.utn.TPfinal.utils.TestUtils.aAddress;
import static com.utn.TPfinal.utils.TestUtils.aFee;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class AddressControllerTest {
    @Mock
    AddressService addressService;

    AddressController addressController;

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
        assertEquals(EntityURLBuilder.buildURL("address", aAddress().getIdAddress()),
                response.getHeaders().getLocation());
    }

    @Test
    void updateAddressHapptyPathTest() {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(addressService.updateAddress(1, aAddress())).thenReturn(aAddress());

        //when
        ResponseEntity response = addressController.updateAddress(1, aAddress());

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(EntityURLBuilder.buildURL("address", aAddress().getIdAddress()),
                response.getBody());

    }

    @Test
    void deleteAddressHapptyPathTest() {
        ResponseEntity response=  addressController.deleteAddress(aAddress().getIdAddress());
        verify(addressService, times(1)).deleteAddress(aAddress().getIdAddress());
    }
}