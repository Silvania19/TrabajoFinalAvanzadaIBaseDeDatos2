package com.utn.TPfinal.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Builder

public class ConsuptionDto {
    Double valuekw;
    Double amount;
}
