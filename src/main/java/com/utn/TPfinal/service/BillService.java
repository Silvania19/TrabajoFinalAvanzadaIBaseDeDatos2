package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Bill;
import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service

public class BillService {

    @Autowired
    BillRepository billRepository;

    public List<Bill> getBillsByRangeOfDates(Date beginDate, Date endDate) {
        return billRepository.findAllBillsByDateBetween(beginDate, endDate);
    }
}
