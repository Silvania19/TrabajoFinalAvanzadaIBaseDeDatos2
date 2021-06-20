package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.repository.BillRepository;
import com.utn.TPfinal.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BillServiceTest {

    private BillService billService;

    private BillRepository billRepository;

    //private static List<Bill> BILL_LIST =  List.of(Bill.builder().firstMeasurement("03-26-2020").lastMeasurement("03-27-2020").build());

    @BeforeEach
    public void setUp() {
        billRepository = mock(BillRepository.class);
        billService = new BillService(billRepository);
    }

    @Test
    public void getBillsByUserAndDateBetweenTest() throws ParseException {

        //given
        Pageable pageable = PageRequest.of(1, 10);
        Integer idClient = 1;

        //when
        when(billRepository.findAllBillsByUserAndDateBetween(any(), any(), any(), any()))
                .thenReturn(TestUtils.aPageBills());

        Page<Bill> pageOfBills =
                billService.getBillsByUserAndDateBetween(idClient, TestUtils.aDate1(), TestUtils.aDate2(), pageable);

        //then
        assertEquals(TestUtils.aPageBills(), pageOfBills);
    }
}