package com.utn.TPfinal.repository;

import com.utn.TPfinal.domain.Measuring;

import com.utn.TPfinal.domain.Meter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MeasuringRepository extends JpaRepository<Measuring, Integer> {

    /* client 5) Consulta de mediciones por rango de fecha */ /** agregar native query **/
    @Query(value= "SELECT * FROM measurings mea\n" +
            "INNER JOIN meters met ON mea.serial_number = met.serial_number\n" +
            "INNER JOIN addresses a ON a.id_address = met.id_address\n" +
            "INNER JOIN clients c ON c.id = a.id_client\n" +
            "WHERE mea.date BETWEEN :beginDate AND :endDate AND c.id = :idClient;", nativeQuery = true)
    Page<Measuring> findMeasuringsByRangeOfDatesAndClient(Integer idClient, Date beginDate, Date endDate, Pageable pageable);
}
