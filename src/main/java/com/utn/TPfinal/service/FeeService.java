package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Fee;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.exception.NotFoundException;
import com.utn.TPfinal.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService {

    FeeRepository feeDao;

    @Autowired
    public  FeeService(FeeRepository feeDao){
        this.feeDao = feeDao;
    }

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
            feeOld.setPriceFee(fee.getPriceFee());
            Fee feeAct=feeDao.save(feeOld);
            return feeAct;
        } else {
            throw new FeeException("Error, el id no existe");
        }

    }
    public Fee getByID(Integer id) throws NotFoundException{
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
