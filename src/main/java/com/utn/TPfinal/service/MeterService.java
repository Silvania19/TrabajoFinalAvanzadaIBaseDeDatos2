package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.*;
import com.utn.TPfinal.exception.*;
import com.utn.TPfinal.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class MeterService {

    MeterRepository meterDao;

    @Autowired
    public MeterService(MeterRepository meterRepository){
        this.meterDao=meterRepository;
    }

    public Meter add(Meter meter) throws MeterExitsException {
        try {
            if (meterDao.findBySerialNumber(meter.getSerialNumber()) == null) {
                return meterDao.save(meter);
            }
            else {
                throw new MeterExitsException("Error en agregar. Datos no correctos");
            }
        }catch (Exception e){
            throw  new MeterException("El serial, number ya existe");
        }


    }

   /* public Meter getSerialNumber(String serialNumber) {
        return meterDao.findBySerialNumber(serialNumber);
    }*/

    public Meter updateMeter(String serialNumber, Meter meter) {
        Meter meterOld=meterDao.findBySerialNumber(serialNumber);
        if (meterOld != null) {
            meterOld.setPasswordMeter(meter.getPasswordMeter());
            Meter meterActual=meterDao.save(meterOld);
            return meterActual;
        } else {
            throw new MeterException("incorrecto");
        }

    }

    public void deleteMeter(String serialNumber) throws MeterException,  MeterWithMeasuringsException  {

     try {
         Meter meter = meterDao.findBySerialNumber(serialNumber);
         if (meter != null) {
             meterDao.deleteBySerialNumber(serialNumber);
         } else {
             throw new MeterException("error");
         }
      }catch (Throwable t){
         throw new MeterWithMeasuringsException("error");
      }
    }

    public Meter findBySerialNumberAndPasswordMeter(String serialNumber, String password) {
        Meter meter= meterDao.findBySerialNumberAndPasswordMeter(serialNumber,password);
        return meter;
    }

}
