package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class FeeService {

    FeeRepository feeDao;

    @Autowired
    public  FeeService(FeeRepository feeDao){
        this.feeDao = feeDao;
    }

    public Fee add(Fee fee) throws FeeException {
            return feeDao.save(fee);
    }

    public Fee updateFee(Integer id, Fee fee)throws FeeException {
        Optional<Fee> feeOld= feeDao.findById(id);
        if (feeOld.isPresent()) {
            feeOld.get().setTypeFee(fee.getTypeFee());
            feeOld.get().setPriceFee(fee.getPriceFee());
            Fee feeAct=feeDao.save(feeOld.get());
            return feeAct;
        } else {
            throw new FeeException("Error, el id no existe");
        }

    }

    public void deleteFee(Integer id) throws FeeException   {
        Optional<Fee> dfee= feeDao.findById(id);
        if (dfee.isPresent()){
            feeDao.delete(dfee.get());
        } else {
            throw new FeeException("Error, el id no existe");
        }


    }
}
