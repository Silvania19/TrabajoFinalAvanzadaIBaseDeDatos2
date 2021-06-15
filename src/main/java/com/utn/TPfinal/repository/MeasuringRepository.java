package com.utn.TPfinal.repository;

import com.utn.TPfinal.domain.Measuring;

import com.utn.TPfinal.projecciones.Consumption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface MeasuringRepository extends JpaRepository<Measuring, Integer> {

   @Query(value = "SELECT SUM(mea.value) as totalkwh ,SUM(mea.price_measuring) as priceTotal \n" +
           "FROM measurings AS mea\n" +
           "INNER JOIN meters AS met ON mea.serial_number = met.serial_number\n" +
           "INNER JOIN addresses AS a ON met.id_address = a.id_address\n" +
           "INNER JOIN clients AS c ON c.id = a.id_client\n" +
           "WHERE c.id = :id  AND mea.date BETWEEN :beginDate AND :lastDate\n" +
           "GROUP BY(c.id);", nativeQuery = true)
   Consumption consumption(Integer id, Date beginDate, Date lastDate);

@Query(value = "SELECT * FROM measurings AS mea\n" +
        "INNER JOIN meters AS met ON mea.serial_number= met.serial_number\n" +
        "INNER JOIN addresses AS a ON met.id_address = a.id_address\n" +
        "WHERE a.id_address =:idAddress AND mea.date BETWEEN :beginDate AND :endDate", nativeQuery = true)
   Page<Measuring> getMeasuringByAddressAndRangeDate(Integer idAddress, Date beginDate, Date endDate, Pageable pageable);
}
