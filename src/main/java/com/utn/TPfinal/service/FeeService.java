package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.persistence.FeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service

public class FeeService {
    @Autowired
    FeeDao feeDao;

    public Fee add(Fee fee) throws FeeException {
        if (!feeDao.existsById(fee.getId_fee())) {
            return feeDao.save(fee);
        } else {
            throw new FeeException("Error");
        }
    }

    public Fee updateFee(Integer id, Fee fee)throws FeeException {

        if (feeDao.existsById(id)) {
            Fee feeOld=getByID(id);
            feeOld.setType_fee(fee.getType_fee());
            Fee feeAct=feeDao.save(feeOld);
            return feeAct;
        } else {
            throw new FeeException("Error, el id no existe");
        }

    }
    public Fee getByID(Integer id) {
        return feeDao.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteFee(Integer id) throws FeeException   {
        if (feeDao.existsById(id) ){
             Fee feeDelete=getByID(id);
            feeDao.delete(feeDelete);

        } else {
            throw new FeeException("Error, el id no existe");
        }


    }
}
