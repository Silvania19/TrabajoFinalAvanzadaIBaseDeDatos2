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
@Table(name= "brands")
public class Brands {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer idBrand;
    private String description;


}
