package com.utn.TPfinal.domain.dto;

import lombok.*;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeasuringDto {

    String serialNumber;
    float value;
    Date date;//date tipo
    String password;//no lo tiene el measurig
}
