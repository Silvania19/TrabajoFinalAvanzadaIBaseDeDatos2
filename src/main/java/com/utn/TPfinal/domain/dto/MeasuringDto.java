package com.utn.TPfinal.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeasuringDto {

    String serialNumber;
    float value;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date date;//date tipo
    String password;//no lo tiene el measurig
}
