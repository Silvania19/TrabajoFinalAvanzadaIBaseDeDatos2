package com.utn.TPfinal.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity
public class Employee extends Person{

    @Override
    public TypePerson typePersona() {
        return TypePerson.EMPLOYEE;
    }

}
