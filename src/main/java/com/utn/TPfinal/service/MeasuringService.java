package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.domain.consumptions;
import com.utn.TPfinal.repository.MeasuringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Page<Measuring> findMeasuringsByRangeOfDatesAndClient(Integer idClient, LocalDateTime beginDate, LocalDateTime endDate, Pageable pageable) {
        return measuringRepository.findMeasuringsByRangeOfDatesAndClient(idClient, beginDate, endDate, pageable);
    }

    public consumptions consumption(Integer id, LocalDateTime beginDate, LocalDateTime lastDate) {
        return  measuringRepository.consumption(id, beginDate, lastDate);
    }
    public Page<Measuring> measuringRangeDateByAddress(Integer idAddress, LocalDateTime beginDate, LocalDateTime endDate, Pageable pageable) {
        return measuringRepository.getMeasuringByAddressAndRangeDate(idAddress, beginDate, endDate, pageable);
    }
}
