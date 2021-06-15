package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.repository.MeasuringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public Page<Measuring> findMeasuringsByRangeOfDatesAndClient(Integer idClient, Date beginDate, Date endDate, Pageable pageable){
        return measuringRepository.findMeasuringsByRangeOfDatesAndClient(idClient, beginDate, endDate, pageable);
    }
}
