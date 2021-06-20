package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.*;
import com.utn.TPfinal.exception.FeeException;
import com.utn.TPfinal.exception.MeterException;
import com.utn.TPfinal.exception.MeterWithMeasuringsException;
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

    public Meter add(Meter meter) throws MeterException {
        try {
            if (meterDao.findBySerialNumber(meter.getSerialNumber()) == null) {
                return meterDao.save(meter);
            }
            else {
                throw new MeterException("Error en agregar. Datos no correctos");
            }
        }catch (Exception e){
            throw  new MeterException("Error al cargar revisa la direccion");
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
