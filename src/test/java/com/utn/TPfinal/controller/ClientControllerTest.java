package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.service.BillService;
import com.utn.TPfinal.service.ClientService;
import com.utn.TPfinal.service.MeasuringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.utn.TPfinal.utils.Constants.NUMBER_OF_ID_ONE;
import static com.utn.TPfinal.utils.Constants.SIZE_TEN;
import static com.utn.TPfinal.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testGetBillsByRangeOfDatesOk(){
        //given
        List<Bill> billList2 = List.of(Bill.builder().idBill(NUMBER_OF_ID_ONE).amount(100.0).build());
        Page<Bill> mockedPage = mock(Page.class);
        Date beginDate = mock(Date.class);
        Date endDate = mock(Date.class);
        Authentication authentication = mock(Authentication.class);

        when(modelMapper.map(authentication.getPrincipal(), UserDto.class)).thenReturn(aUserDto());
        when(mockedPage.getTotalElements()).thenReturn(10L);
        when(mockedPage.getTotalPages()).thenReturn(1);
        when(mockedPage.getContent()).thenReturn(billList2);
        when(billService.getBillsByUserAndDateBetween(aUserDto().getId(), beginDate, endDate, aPageable())).thenReturn(mockedPage);

        //then
        ResponseEntity<List<Bill>> response = clientController.getBillsByRangeOfDatesByUser(aUserDto().getId(),authentication,beginDate, endDate, aPageable());

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10L, Long.parseLong(response.getHeaders().get("X-Total-Count").get(0)));
        assertEquals(1, Integer.parseInt(response.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(billList2, response.getBody());
    }

    @Test
    public void testGetBillsByRangeOfDatesUnauthorized(){
        //given
        List<Bill> billList2 = List.of(Bill.builder().idBill(NUMBER_OF_ID_ONE).amount(100.0).build());
        Date beginDate = mock(Date.class);
        Date endDate = mock(Date.class);
        Page<Bill> mockedPage = mock(Page.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.getPrincipal()).thenReturn(aUserDto());
        when(billService.getBillsByUserAndDateBetween(aUserDto().getId(), beginDate, endDate, aPageable())).thenReturn(mockedPage);

        //then
        ResponseEntity<List<Bill>> response = clientController.getBillsByRangeOfDatesByUser(aUserDto().getId(),authentication, beginDate, endDate, aPageable());

        //assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testGetBillsByRangeOfDatesNoContent(){

        //given
        Integer idClient = NUMBER_OF_ID_ONE;
        Page<Bill> mockedPage = mock(Page.class);
        Date beginDate = mock(Date.class);
        Date endDate = mock(Date.class);
        Authentication authentication = mock(Authentication.class);

        when(modelMapper.map(authentication.getPrincipal(), UserDto.class)).thenReturn(aUserDto());
        when(mockedPage.getContent()).thenReturn(Collections.emptyList());
        when(billService.getBillsByUserAndDateBetween(idClient, beginDate, endDate, aPageable())).thenReturn(mockedPage);

        //then
        ResponseEntity<List<Bill>> response = clientController.getBillsByRangeOfDatesByUser(aUserDto().getId(),authentication, beginDate, endDate, aPageable());

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
    public void billsNotPayUnauthorized(){
        Integer idClient=3;
        Authentication authentication= mock(Authentication.class);
        List<Bill> bills= new ArrayList<>();
        when(modelMapper.map(authentication.getPrincipal(), Client.class)).thenReturn(aClient2());
        when(billService.getBillsByIdClientNotPay(aClient2().getId())).thenReturn(bills);

        ResponseEntity response = clientController.billsNotPay(idClient,authentication);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

   /* @Test
    public  void consumptionRangeDateKwMoneyOK() {
       Authentication authentication=  mock(Authentication.class);

       when(modelMapper.map(authentication.getPrincipal(), UserDto.class)).thenReturn(aUserDto());
       when(measuringService.consumption(aUserDto().getId(), aDate1(), aDate2())).thenReturn();
    }

    */

   @Test
    public void getMeasuringsByRangesOfDatesOK() {

        //GIVEN
        Date beginDate = mock(Date.class);
        Date endDate = mock(Date.class);
        Authentication authentication= mock(Authentication.class);
        Page<Measuring> mockedPage = mock(Page.class);

        when(modelMapper.map(authentication.getPrincipal(), UserDto.class)).thenReturn(aUserDto());
        when(measuringService.findMeasuringsByRangeOfDatesAndClient(aUserDto().getId(), beginDate, endDate, aPageable()))
                .thenReturn(aPageMeasuring());
       when(mockedPage.getContent()).thenReturn(aListMeasuring());

        //WHEN
        ResponseEntity<List<Measuring>> responseEntity = clientController.getMeasuringsByRangeOfDates(aUserDto().getId(),
                                                         authentication, beginDate, endDate, aPageable());
        //THEN

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), aListMeasuring());

    }

    @Test
    public void getMeasuringsByRangesOfDatesUnauthorize() {

        //GIVEN
        Date beginDate = mock(Date.class);
        Date endDate = mock(Date.class);
        Authentication authentication= mock(Authentication.class);
        Page<Measuring> mockedPage = mock(Page.class);

        when(modelMapper.map(authentication.getPrincipal(), UserDto.class)).thenReturn(aUserDto());
        when(measuringService.findMeasuringsByRangeOfDatesAndClient(aUserDto().getId(), beginDate, endDate, aPageable()))
                .thenReturn(aPageMeasuring());
        when(mockedPage.getContent()).thenReturn(aListMeasuring());

        //WHEN
        ResponseEntity<List<Measuring>> responseEntity = clientController.getMeasuringsByRangeOfDates(4,
                authentication, beginDate, endDate, aPageable());
        //THEN

        assertEquals(responseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
        assertEquals(responseEntity.getBody(), null);

    }

   @Test
    public void getUnpaidBillsByClientAndAddressOK(){
       Integer idAddress=1;
       Integer idClient=1;
       List<Bill> bills= List.of(aBill());
       when(billService.getUnpaidBillsByClientIdAndAddressId(idClient, idAddress, aPageable()))
               .thenReturn(aPageBills());

       ResponseEntity responseEntity= clientController.getUnpaidBillsByClientAndAddress(idClient, idAddress, aPageable());

       assertEquals(responseEntity.getBody(), bills);
       assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
   }

   @Test
   public void moreConsumersOfDateRange(){
        Date beginDate = mock(Date.class);
        Date endDate = mock(Date.class);
       when(clientService.tenMoreConsumers(beginDate, endDate)).thenReturn(aListUser());

       when(modelMapper.map(any(Client.class),eq(UserDto.class))).thenReturn(aUserDto());


        ResponseEntity responseEntity= clientController.moreConsumersOfDateRange(beginDate, endDate);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), aListUserDto());
   }


}
