package com.utn.TPfinal.domain.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeasuringDto {

    String serialNumber;
    float value;
    String date;
    String password;
}
