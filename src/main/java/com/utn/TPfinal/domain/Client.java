package com.utn.TPfinal.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="clients")
public class Client extends Person{

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    List<Bill>bills;

    //4) consulta de facturas impagas por cliente y domicilio. Examinar todo.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    List<Address> addresses;
}
