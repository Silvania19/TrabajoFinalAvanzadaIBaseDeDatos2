package com.utn.TPfinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="models")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer idModel;
    private  String description;

    //fk with brand
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_brand", nullable = false)
    private Brands brands;



}
