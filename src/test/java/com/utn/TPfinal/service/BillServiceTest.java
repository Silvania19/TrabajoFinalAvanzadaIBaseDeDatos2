package com.utn.TPfinal.service;

import com.utn.TPfinal.controller.BillController;
import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.repository.BillRepository;
import com.utn.TPfinal.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class BillServiceTest {

    @Mock
    private BillService billService;

    @Mock
    private BillRepository billRepository;

    //private static List<Bill> BILL_LIST =  List.of(Bill.builder().firstMeasurement("03-26-2020").lastMeasurement("03-27-2020").build());

    @BeforeEach
    public void setUp() {
        initMocks(this);

    }

    @Test
    public void getBillsByUserAndDateBetweenTest() throws ParseException {

        //given
        Pageable pageable = PageRequest.of(1, 10);

        Integer idClient = 1;

        //List<Bill> billList2 = List.of(Bill.builder().firstMeasurement(date1).lastMeasurement(date2).build());

        //Page<Bill> mockedPage = mock(Page.class);

        //Date beginDate = mock(Date.class);
        //Date endDate = mock(Date.class);

        //when

        when(billRepository.findAllBillsByUserAndDateBetween(idClient, TestUtils.aDate1(), TestUtils.aDate2(), pageable))
                .thenReturn(TestUtils.aPageBills());
        // arreglar el thenReturn de arriba

        Page<Bill> pageOfBills =
                billRepository.findAllBillsByUserAndDateBetween(idClient, TestUtils.aDate1(), TestUtils.aDate2(), pageable);

        //then
        /*Mockito.verify(billRepository,Mockito.times(1))
                .findAllBillsByUserAndDateBetween(idClient, TestUtils.aDate1(), TestUtils.aDate2(), pageable);*/
        assertEquals(TestUtils.aPageBills(), pageOfBills);

    }



}