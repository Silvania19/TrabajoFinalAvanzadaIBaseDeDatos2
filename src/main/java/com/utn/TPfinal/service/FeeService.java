package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.exception.NotFoundException;
import com.utn.TPfinal.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service

public class FeeService {
    @Autowired
    FeeRepository feeDao;

    public Fee add(Fee fee) throws FeeException {
        if (!feeDao.existsById(fee.getIdFee())) {
            return feeDao.save(fee);
        } else {
            throw new FeeException("Error");
        }
    }

    public Fee updateFee(Integer id, Fee fee)throws FeeException {

        if (feeDao.existsById(id)) {
            Fee feeOld=getByID(id);
            feeOld.setTypeFee(fee.getTypeFee());
            Fee feeAct=feeDao.save(feeOld);
            return feeAct;
        } else {
            throw new FeeException("Error, el id no existe");
        }

    }
    public Fee getByID(Integer id) throws HttpClientErrorException{
        return feeDao.findById(id)
                .orElseThrow(() -> new NotFoundException(""));
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
