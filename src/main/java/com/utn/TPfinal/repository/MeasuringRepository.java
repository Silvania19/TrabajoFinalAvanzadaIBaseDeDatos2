package com.utn.TPfinal.repository;

import com.utn.TPfinal.domain.Measuring;

import com.utn.TPfinal.projections.MeasuringDtoQuery;
import com.utn.TPfinal.projections.Consumption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface MeasuringRepository extends JpaRepository<Measuring, Integer> {


    /* client 5) Consulta de mediciones por rango de fecha */
    @Query(value= "SELECT mea.value, mea.date, mea.price_measuring as priceMeasuring " +
            "FROM measurings mea\n" +
            "INNER JOIN meters met ON mea.serial_number = met.serial_number\n" +
            "INNER JOIN addresses a ON a.id_address = met.id_address\n" +
            "INNER JOIN clients c ON c.id = a.id_client\n" +
            "WHERE mea.date BETWEEN :beginDate AND :endDate AND c.id = :idClient", nativeQuery = true)
    Page<MeasuringDtoQuery> findMeasuringsByRangeOfDatesAndClient(Integer idClient, Date beginDate, Date endDate, Pageable pageable);

    /* client 4) Consulta de consumo por rango de fechas (el usuario va a ingresar un rango
    de fechas y quiere saber cuánto consumió en ese periodo en Kwh y dinero) */

   @Query(value = "SELECT SUM(mea.value) as totalkwh ,SUM(mea.price_measuring) as priceTotal \n" +
           "FROM measurings AS mea\n" +
           "INNER JOIN meters AS met ON mea.serial_number = met.serial_number\n" +
           "INNER JOIN addresses AS a ON met.id_address = a.id_address\n" +
           "INNER JOIN clients AS c ON c.id = a.id_client\n" +
           "WHERE c.id = :id  AND mea.date BETWEEN :beginDate AND :lastDate\n" +
           "GROUP BY(c.id);", nativeQuery = true)
   Consumption consumption(Integer id, Date beginDate, Date lastDate);

   /* backoffice 6) Consulta de mediciones de un domicilio por rango de fechas */

   @Query(value = "SELECT value, date, price_measuring as priceMeasuring FROM measurings `mea`\n" +
        "INNER JOIN meters met ON mea.serial_number = met.serial_number\n" +
        "INNER JOIN addresses a ON met.id_address = a.id_address\n" +
        "WHERE a.id_address = :idAddress and mea.date between :beginDate and :endDate", nativeQuery = true)
    Page<MeasuringDtoQuery> getMeasuringByAddressAndRangeDate(Integer idAddress, Date beginDate, Date endDate, Pageable pageable);
}
