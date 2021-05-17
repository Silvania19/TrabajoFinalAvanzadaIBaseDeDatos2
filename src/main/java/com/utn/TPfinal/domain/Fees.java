package com.utn.TPfinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="fees")
public class Fees{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_fees;
    private String type_fees;

    //lista de meter
}
