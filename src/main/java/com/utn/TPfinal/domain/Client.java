package com.utn.TPfinal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="clients")
public class Client extends User{

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    List<Bill>bills;

    //4) consulta de facturas impagas por cliente y domicilio. Examinar todo.
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    List<Address> addresses;


}
