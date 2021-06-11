package com.utn.TPfinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="measurings")
public class Measuring{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMeasuring;
    private Integer measurement;
    private Date time;
    // fk with bill
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_bill", nullable = false)
    private Bill bill;
    //fk with meter

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="serial_number", nullable = false)
    private Meter meter;
}
