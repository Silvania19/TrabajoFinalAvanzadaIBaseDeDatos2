package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Bill;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.utn.TPfinal.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.Access;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service

public class BillService {

    BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository){
        this.billRepository = billRepository;
    }

    public Page<Bill> getBillsByUserAndDateBetween(Integer idClient, LocalDateTime beginDate, LocalDateTime endDate, Pageable pageable) {
        return billRepository.findAllBillsByUserAndDateBetween(idClient, beginDate, endDate, pageable);
    }

     public Page<Bill> getUnpaidBillsByClientIdAndAddressId(Integer idClient, Integer idAddress, Pageable pageable) {
        return billRepository.findUnpaidBillsByClientIdAndAddressId(idClient, idAddress, pageable);
    }

    public List<Bill> getBillsByIdClientNotPay(Integer idClient) {
        return billRepository.findByClientIdNotPay(idClient);
    }
}
