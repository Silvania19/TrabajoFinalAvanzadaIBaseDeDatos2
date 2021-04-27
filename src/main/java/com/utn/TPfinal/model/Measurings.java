package com.utn.TPfinal.model;

import java.util.Date;

public class Measurings {
    private Integer id_measuring;
    private Integer measurement;
    private Date time;
    // fk with bill
    private Integer id_bill;
    //fk with meter
    private Integer id_meter;
}
