package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.service.ClientService;
import com.utn.TPfinal.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.utn.TPfinal.utils.TestUtils.aBill;
import static com.utn.TPfinal.utils.TestUtils.aClient2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientControllerTest {

    ClientService clientService;
   ClientController clientController;
   ModelMapper modelMapper;
   BillController billController;

   @BeforeEach
   public void setUp(){
       this.modelMapper= mock(ModelMapper.class);
       this.billController= mock(BillController.class);
       this.clientService= mock(ClientService.class);
       clientController= new ClientController(clientService, modelMapper, billController);
   }
   @Test
    public void billsNotPayOk() {
       Authentication authentication= mock(Authentication.class);
       List<Bill> bills= List.of(aBill());
       when(modelMapper.map(authentication.getPrincipal(), Client.class)).thenReturn(aClient2());
       when(billController.getBillsNotPay(aClient2().getId())).thenReturn(bills);

       ResponseEntity response= clientController.billsNotPay(authentication);

       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertEquals(bills, response.getBody());

   }
    @Test
    public void billsNotPayNoContent(){
        Authentication authentication= mock(Authentication.class);
        List<Bill> bills= new ArrayList<>();
        when(modelMapper.map(authentication.getPrincipal(), Client.class)).thenReturn(aClient2());
        when(billController.getBillsNotPay(aClient2().getId())).thenReturn(bills);

        ResponseEntity response= clientController.billsNotPay(authentication);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());


    }
    @Test
    public void billsNotPayNotFound() {
        Authentication authentication= mock(Authentication.class);
        List<Bill> bills= new ArrayList<>();
        when(modelMapper.map(authentication.getPrincipal(), Client.class)).thenReturn(null);
        when(billController.getBillsNotPay(aClient2().getId())).thenReturn(bills);

        ResponseEntity response= clientController.billsNotPay(authentication);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());


    }
}
