package com.utn.TPfinal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
public interface MeasuringDtoQuery {

    float getValue();
    Date getDate();
    double getPriceMeasuring();
}
