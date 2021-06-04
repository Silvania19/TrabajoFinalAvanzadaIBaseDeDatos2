package com.utn.TPfinal.domain;

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
    private Integer idAddress;

    private String nameAddress;
    private String numberAddress;

    //fk with client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_client", nullable = false)
    private Client client;

    @OneToOne(mappedBy="address")
    private Meter meter;
}
