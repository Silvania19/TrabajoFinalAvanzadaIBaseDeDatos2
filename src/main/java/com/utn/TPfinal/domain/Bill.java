package com.utn.TPfinal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_bill;

    private Double amount;
    //this variable is use to decide when the bill is pay or not pay. True=pay False=Not pay
    private  Boolean pay;
    // para determinar si la factura esta paga o no. Consulta de deuda (Facturas impagas)

    private Date firstMeasurement;
    private Date lastMeasurement;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_client", nullable = false)
    private Client client;

}
