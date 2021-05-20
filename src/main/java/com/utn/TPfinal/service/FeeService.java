package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.persistence.FeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class FeeService {
    @Autowired
    FeeDao feeDao;

    public Fee add(Fee fee) {
        return feeDao.save(fee);
    }
}
