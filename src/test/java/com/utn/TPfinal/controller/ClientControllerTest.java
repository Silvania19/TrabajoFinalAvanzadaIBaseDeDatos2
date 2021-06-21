package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.service.BillService;
import com.utn.TPfinal.service.ClientService;
import com.utn.TPfinal.service.MeasuringService;
import com.utn.TPfinal.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static com.utn.TPfinal.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.Mockito.*;

public class ClientControllerTest {

    ClientService clientService;
    ClientController clientController;
    ModelMapper modelMapper;
    MeasuringService measuringService;
    BillService billService;

   @BeforeEach
   public void setUp(){
       this.modelMapper= mock(ModelMapper.class);
       this.measuringService= mock(MeasuringService.class);
       this.clientService= mock(ClientService.class);
       this.billService= mock(BillService.class);
       clientController= new ClientController(clientService, modelMapper, billService, measuringService);
   }

    @Test
    public void testGetBillsByRangeOfDatesOk() throws ParseException {
        //given
        Pageable pageable = PageRequest.of(1, 10);
        List<Bill> billList2 = List.of(Bill.builder().firstMeasurement(aDate1()).lastMeasurement(aDate2()).build());

        Page<Bill> mockedPage = mock(Page.class);
        Date beginDate = mock(Date.class);
        Date endDate = mock(Date.class);
        Authentication authentication = mock(Authentication.class);

        when(modelMapper.map(authentication.getPrincipal(), UserDto.class)).thenReturn(aUserDto());
        when(mockedPage.getTotalElements()).thenReturn(10L);
        when(mockedPage.getTotalPages()).thenReturn(1);
        when(mockedPage.getContent()).thenReturn(billList2);
        when(billService.getBillsByUserAndDateBetween(aUserDto().getId(), aDate1(), aDate2(), pageable)).thenReturn(mockedPage);

        //then
        ResponseEntity<List<Bill>> response = clientController.getBillsByRangeOfDatesByUser(aUserDto().getId(),authentication, aDate1(), aDate2(), pageable);

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10L, Long.parseLong(response.getHeaders().get("X-Total-Count").get(0)));
        assertEquals(1, Integer.parseInt(response.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(billList2, response.getBody());
    }
    @Test
    public void testGetBillsByRangeOfDatesUnauthorized() throws ParseException {
        //given
        Pageable pageable = PageRequest.of(1, 10);
        List<Bill> billList2 = List.of(Bill.builder().firstMeasurement(aDate1()).lastMeasurement(aDate2()).build());

        Page<Bill> mockedPage = mock(Page.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.getPrincipal()).thenReturn(aUserDto());
        when(billService.getBillsByUserAndDateBetween(aUserDto().getId(), aDate1(), aDate2(), pageable)).thenReturn(mockedPage);

        //then
        ResponseEntity<List<Bill>> response = clientController.getBillsByRangeOfDatesByUser(aUserDto().getId(),authentication, aDate1(), aDate2(), pageable);

        //assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testGetBillsByRangeOfDatesNoContent(){

        //given
        Pageable pageable = PageRequest.of(50, 10);
        Integer idClient = 1;

        Page<Bill> mockedPage = mock(Page.class);
        Date beginDate = mock(Date.class);
        Date endDate = mock(Date.class);
        Authentication authentication = mock(Authentication.class);

        when(modelMapper.map(authentication.getPrincipal(), UserDto.class)).thenReturn(aUserDto());
        when(mockedPage.getContent()).thenReturn(Collections.emptyList());
        when(billService.getBillsByUserAndDateBetween(idClient, beginDate, endDate, pageable)).thenReturn(mockedPage);

        //then
        ResponseEntity<List<Bill>> response = clientController.getBillsByRangeOfDatesByUser(aUserDto().getId(),authentication, beginDate, endDate, pageable);

        //assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }


    @Test
    public void billsNotPayOk() {
        Integer idClient=1;
        Authentication authentication= mock(Authentication.class);
        List<Bill> bills= List.of(aBill());
        when(modelMapper.map(authentication.getPrincipal(), Client.class)).thenReturn(aClient2());
        when(billService.getBillsByIdClientNotPay(aClient2().getId())).thenReturn(bills);

        ResponseEntity response= clientController.billsNotPay(idClient,authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bills, response.getBody());

    }
    @Test
    public void billsNotPayNoContent(){
        Integer idClient=1;
        Authentication authentication= mock(Authentication.class);
        List<Bill> bills= new ArrayList<>();
        when(modelMapper.map(authentication.getPrincipal(), Client.class)).thenReturn(aClient2());
        when(billService.getBillsByIdClientNotPay(aClient2().getId())).thenReturn(bills);

        ResponseEntity response= clientController.billsNotPay(idClient,authentication);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());


    }
    @Test
    public void billsNotPayNotFound() {
        Integer idClient=1;
        Authentication authentication= mock(Authentication.class);
        List<Bill> bills= new ArrayList<>();
        when(modelMapper.map(authentication.getPrincipal(), Client.class)).thenReturn(null);
        when(billService.getBillsByIdClientNotPay(aClient2().getId())).thenReturn(bills);

        ResponseEntity response= clientController.billsNotPay(idClient, authentication);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());


    }
   /* @Test
    public  void consumptionRangeDateKwMoneyOK() throws ParseException {
       Authentication authentication=  mock(Authentication.class);

       when(modelMapper.map(authentication.getPrincipal(), UserDto.class)).thenReturn(aUserDto());
       when(measuringService.consumption(aUserDto().getId(), aDate1(), aDate2())).thenReturn();
    }

    */

   @Test
    public void getMeasuringsByRangesOfDatesOK() throws ParseException {

        //GIVEN
        Pageable pageable = PageRequest.of(1, 10);
        Authentication authentication= mock(Authentication.class);
        Page<Measuring> mockedPage = mock(Page.class);
        when(modelMapper.map(authentication.getPrincipal(), UserDto.class)).thenReturn(aUserDto());
        when(measuringService.findMeasuringsByRangeOfDatesAndClient(aUserDto().getId(), aDate1(), aDate2(), pageable))
                .thenReturn(aPageMeasuring());
       when(mockedPage.getContent()).thenReturn(aListMeasuring());

        //WHEN
        ResponseEntity<List<Measuring>> responseEntity = clientController.getMeasuringsByRangeOfDates(aUserDto().getId(),
                                                         authentication, aDate1(), aDate2(), pageable);
        //THEN
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), aListMeasuring());
    }

   @Test
    public void getUnpaidBillsByClientAndAddressOK(){
       Integer idAddress=1;
       Integer idClient=1;
       Pageable pageable = PageRequest.of(1, 10);
       List<Bill> bills= List.of(aBill());
       when(billService.getUnpaidBillsByClientIdAndAddressId(idClient, idAddress, pageable))
               .thenReturn(aPageBills());

       ResponseEntity responseEntity= clientController.getUnpaidBillsByClientAndAddress(idClient, idAddress, pageable);

       assertEquals(responseEntity.getBody(), bills);
       assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
   }
   /*@Test
   public void moreConsumersOfDateRange() throws ParseException {

       when(clientService.tenMoreConsumers(aDate1(), aDate2()))/*.stream().map(o-> modelMapper.map(o, UserDto.class)))
               .thenReturn(aListUser());

        ResponseEntity responseEntity= clientController.moreConsumersOfDateRange(aDate1(), aDate2());

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), aListUserDto());
   }*/
}
