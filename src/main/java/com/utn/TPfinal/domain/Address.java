package com.utn.TPfinal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_address;

    private String name_address;
    private String number_address;

    //fk with client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_person", nullable = false)
    private Client client;

    @OneToOne(mappedBy="address")
    private Meter meter;
}
