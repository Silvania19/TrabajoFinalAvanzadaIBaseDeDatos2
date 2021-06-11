package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.*;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (meterDao.findBySerialNumber(meter.getSerialNumber()) != null) {
            return meterDao.save(meter);
        }
        else {
            throw new FeeException("Error en agergar. Datos no correctos");
        }

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
    public Meter getSerialNumber(String serialNumber) {
        return meterDao.findBySerialNumber(serialNumber);
    }

    public Meter updateMeter(String serialNumber, Meter meter) {
        if (meterDao.findBySerialNumber(serialNumber)!= null) {
            Meter meterOld=meterDao.findBySerialNumber(serialNumber);
            meterOld.setSerialNumber(meter.getSerialNumber());
            meterOld.setPasswordMeter(meter.getPasswordMeter());
            Meter meterActual=meterDao.save(meterOld);
            return meterActual;
        } else {
            throw new FeeException("Error, el id no existe");
        }

    }

    public void deleteMeter(String serialNumber) {
        if(meterDao.findBySerialNumber(serialNumber) !=null){

            meterDao.deleteBySerialNumber(serialNumber);
        }
        else {
            throw  new FeeException("error el id no existe");
        }

    }

    public Meter findBySerialNumberAndPasswordMeter(String serialNumber, String password) {
        Meter meter= meterDao.findBySerialNumberAndPasswordMeter(serialNumber,password);
        return meter;
    }

}
