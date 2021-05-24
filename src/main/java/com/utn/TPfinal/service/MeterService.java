package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.*;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.repository.MeterRepository;
import com.utn.TPfinal.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service

public class MeterService {

    @Autowired
    MeterRepository meterDao;
    @Autowired
    FeeService feeService;
    @Autowired
    AddressService addressService;
    @Autowired
    ModelService modelService;

    public Meter add(Meter meter) {

       return meterDao.save(meter);
    }

  /*  public Meter addAddressModelFeeToMeter(Integer idMeter, Integer idAddress, Integer idModel, Integer idFee) {

        Meter meterSearch= getByID(idMeter);
        if (meterDao.existsById(meterSearch.getId_meter())) {
            Address addressSearch = addressService.getByID(idAddress);
            Fee feeSearch= feeService.getByID(idFee);
            Model modelSearch= modelService.getByID(idModel);
            meterSearch.setAddress(addressSearch);
            meterSearch.setFee(feeSearch);
            meterSearch.setModel(modelSearch);
           return meterDao.save(meterSearch);
        } else {
            throw new FeeException("Error en addAddressModelFeeToMeter");
        }
    }*/
    public Meter getByID(Integer id) {
        return meterDao.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Meter updateMeter(Integer idMeter, Meter meter) {
        if (meterDao.existsById(idMeter)) {
            Meter meterOld=getByID(idMeter);
            meterOld.setSerial_number(meter.getSerial_number());
            meterOld.setPassword_meter(meter.getPassword_meter());
            Meter meterActual=meterDao.save(meterOld);
            return meterActual;
        } else {
            throw new FeeException("Error, el id no existe");
        }

    }

    public void deleteMeter(Integer id) {
        if(meterDao.existsById(id)){

            meterDao.deleteById(id);
        }
        else {
            throw  new FeeException("error el id no existe");
        }

    }
}
