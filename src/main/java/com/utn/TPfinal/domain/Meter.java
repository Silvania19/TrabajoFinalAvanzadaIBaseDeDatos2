package com.utn.TPfinal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="meters")
public class Meter {
    @Id
    private String serialNumber;
    private String passwordMeter;
    //fk with model
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_model", nullable = false)
    private Model model;

    // fk with address
    // es no eliminar address cuando elimino un meter
    @OneToOne (cascade = CascadeType.DETACH)
    @JoinColumn(name="id_address")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_fee", nullable = false)
    private Fee fee;



}
