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

import java.util.Date;
import java.util.List;

@Service

public class BillService {

    BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository){
        this.billRepository = billRepository;
    }

    public Page<Bill> getBillsByUserAndDateBetween(Integer idClient, Date beginDate, Date endDate, Pageable pageable) {
        return billRepository.findAllBillsByUserAndDateBetween(idClient, beginDate, endDate, pageable);
    }

     public List<Bill> findUnpaidBillsByClientIdAndAddressId(Integer idClient, Integer idAddress) {
        return billRepository.findUnpaidBillsByClientIdAndAddressId(idClient, idAddress);
    }

    public List<Bill> getBillsByIdClientNotPay(Integer idClient) {
        return billRepository.findByClientIdNotPay(idClient);
    }
}
