package com.utn.TPfinal.controller;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.dto.UserDto;
import com.utn.TPfinal.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.utn.TPfinal.service.BillService;
import org.springframework.security.core.Authentication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class BillControllerTest {

    @Mock
    private BillService billService;

    private BillController billController;

    //private static List<Bill> BILL_LIST =  List.of(Bill.builder().firstMeasurement("03-26-2020").lastMeasurement("03-27-2020").build());

    @BeforeEach
    public void setUp() {
        initMocks(this);
        billController = new BillController(billService);
    }

    @Test
    public void testGetBillsByRangeOfDatesHttpStatus200() throws ParseException {

        //given
        Pageable pageable = PageRequest.of(1, 10);

        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date date1 = simpleDateFormat.parse("03-26-2020");
        Date date2 = simpleDateFormat.parse("03-27-2020");
        Date date3 = simpleDateFormat.parse("04-27-2020");
        Date date4 = simpleDateFormat.parse("04-27-2020");
        Integer idClient = 1;


        List<Bill> billList2 = List.of(Bill.builder().firstMeasurement(date1).lastMeasurement(date2).build(),
                Bill.builder().firstMeasurement(date3).lastMeasurement(date4).build());

        Page<Bill> mockedPage = mock(Page.class);
        Date beginDate = mock(Date.class);
        Date endDate = mock(Date.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.getPrincipal()).thenReturn(UserDto.builder().id(1).build());
        //

        when(mockedPage.getTotalElements()).thenReturn(10L);
        when(mockedPage.getTotalPages()).thenReturn(1);
        when(mockedPage.getContent()).thenReturn(billList2);
        when(billService.getBillsByUserAndDateBetween(idClient, beginDate, endDate, pageable)).thenReturn(mockedPage);

        //then
        ResponseEntity<List<Bill>> response = billController.getBillsByRangeOfDatesByUser(authentication, idClient, beginDate, endDate, pageable);

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10L, Long.parseLong(response.getHeaders().get("X-Total-Count").get(0)));
        assertEquals(1, Integer.parseInt(response.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(billList2, response.getBody());
    }

    /*@Test
    public void testGetBillsByRangeOfDatesNoContent(){

        //given
        Pageable pageable = PageRequest.of(50, 10);

        Page<Bill> mockedPage = mock(Page.class);
        Date beginDate = mock(Date.class);
        Date endDate = mock(Date.class);

        when(mockedPage.getContent()).thenReturn(Collections.emptyList());
        when(billService.getBillsByRangeOfDates(beginDate, endDate, pageable)).thenReturn(mockedPage);

        //then
        ResponseEntity<List<Bill>> response = billController.getBillsByRangeOfDates(beginDate, endDate, pageable);

        //assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }*/
}