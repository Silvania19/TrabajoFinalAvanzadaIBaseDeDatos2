package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Measuring;
import com.utn.TPfinal.repository.MeasuringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
