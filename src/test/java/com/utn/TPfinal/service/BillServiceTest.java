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
import java.util.Date;

import static com.utn.TPfinal.utils.Constants.NUMBER_OF_ID_ONE;
import static com.utn.TPfinal.utils.TestUtils.aPageable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BillServiceTest {

    private BillService billService;

    private BillRepository billRepository;

    @BeforeEach
    public void setUp() {
        billRepository = mock(BillRepository.class);
        billService = new BillService(billRepository);
    }

    @Test
    public void getBillsByUserAndDateBetweenTest() throws ParseException {

        //given
        Integer idClient = NUMBER_OF_ID_ONE;
        Date beginDate = mock(Date.class);
        Date endDate = mock(Date.class);
        //when
        when(billRepository.findAllBillsByUserAndDateBetween(any(), any(), any(), any()))
                .thenReturn(TestUtils.aPageBills());

        Page<Bill> pageOfBills =
                billService.getBillsByUserAndDateBetween(idClient, beginDate, endDate, aPageable());

        //then
        assertEquals(TestUtils.aPageBills(), pageOfBills);
    }
}