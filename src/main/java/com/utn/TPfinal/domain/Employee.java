package com.utn.TPfinal.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name="employees")
public class Employee extends User{

    @Override
    public TypeUser typeUser() {
        return TypeUser.EMPLOYEE;
    }

}
