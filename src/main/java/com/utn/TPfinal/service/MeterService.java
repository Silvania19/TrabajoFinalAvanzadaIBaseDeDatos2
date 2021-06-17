package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.*;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.exception.NotFoundException;
import com.utn.TPfinal.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class MeterService {

    @Autowired
    MeterRepository meterDao;

    @Autowired
    public MeterService(MeterRepository meterRepository){
        this.meterDao=meterRepository;
    }
    public Meter add(Meter meter) {
        if (meterDao.findBySerialNumber(meter.getSerialNumber()) == null) {
            return meterDao.save(meter);
        }
        else {
            throw new FeeException("Error en agregar. Datos no correctos");
        }

    }

    public Meter getSerialNumber(String serialNumber) {
        return meterDao.findBySerialNumber(serialNumber);
    }

    public Meter updateMeter(String serialNumber, Meter meter) {
        Meter meterOld=meterDao.findBySerialNumber(serialNumber);
        if (meterOld != null) {
            meterOld.setPasswordMeter(meter.getPasswordMeter());
            Meter meterActual=meterDao.save(meterOld);
            return meterActual;
        } else {
            throw new NotFoundException("incorrecto");
        }

    }

    public void deleteMeter(String serialNumber) {
        if(meterDao.findBySerialNumber(serialNumber) !=null){
            meterDao.removeBySerialNumber(serialNumber);
          // meterDao.BySerialNumber(serialNumber);
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
