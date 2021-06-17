package com.utn.TPfinal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Integer idAddress;

    @Column(name = "name_address")
    private String nameAddress;

    @Column(name = "number_address")
    private String numberAddress;

    //fk with client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_client", nullable = false)
    private Client client;

    @OneToOne(mappedBy="address")
    private Meter meter;
}
