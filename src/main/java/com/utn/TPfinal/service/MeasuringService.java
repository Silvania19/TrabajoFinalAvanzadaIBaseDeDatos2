package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.projections.MeasuringDtoQuery;
import com.utn.TPfinal.projections.Consumption;
import com.utn.TPfinal.repository.MeasuringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service

public class MeasuringService {
    MeasuringRepository measuringRepository;

    @Autowired
    public  MeasuringService(MeasuringRepository measuringRepository){
        this.measuringRepository=measuringRepository;
    }

    public Measuring add(Measuring measuring) {
        return measuringRepository.save(measuring);
    }

    public Page<Measuring> findMeasuringsByRangeOfDatesAndClient(Integer idClient, Date beginDate, Date endDate, Pageable pageable) {
        return measuringRepository.findMeasuringsByRangeOfDatesAndClient(idClient, beginDate, endDate, pageable);
    }

    public Consumption consumption(Integer id, Date beginDate, Date lastDate) {
        return  measuringRepository.consumption(id, beginDate, lastDate);
    }/*poner en el test los metodos de la interfaz*/

    public Page<MeasuringDtoQuery> measuringRangeDateByAddress(Integer idAddress, Date beginDate, Date endDate, Pageable pageable) {
        return measuringRepository.getMeasuringByAddressAndRangeDate(idAddress, beginDate, endDate, pageable);
    }
}
