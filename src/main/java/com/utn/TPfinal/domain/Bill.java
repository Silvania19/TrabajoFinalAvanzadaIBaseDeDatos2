package com.utn.TPfinal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBill;

    private Double amount;
    //this variable is use to decide when the bill is pay or not pay. True=pay False=Not pay
    private  Boolean pay;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate dateCreate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate expiration;
    private String meterNumber;
    private String typeFee;
    // para determinar si la factura esta paga o no. Consulta de deuda (Facturas impagas)
    @Column(name= "first_measuring")
   // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Double firstMeasurement;
    @Column(name= "last_measuring")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Double lastMeasurement;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_client", nullable = false)
    private Client client;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_address", nullable = false)
    private Address address;
}
