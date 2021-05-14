package com.utn.TPfinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Meter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_meter;

    private String serial_number;
    private String password_meter;
    //fk with model
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_model", nullable = false)
    private Model model;

    // fk with address
    @OneToOne
    @JoinColumn(name="id_address")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_fees", nullable = false)
    private Fees fees;



}
