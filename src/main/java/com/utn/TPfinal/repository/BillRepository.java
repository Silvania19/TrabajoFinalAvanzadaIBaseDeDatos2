package com.utn.TPfinal.repository;

import com.utn.TPfinal.domain.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    /*@Query(value = "SELECT * FROM bills b " +
            "WHERE (b.first_measurement BETWEEN :beginDate AND :endDate) OR " +
            "(b.last_measurement BETWEEN :beginDate AND :endDate);"
            , nativeQuery = true)
    List<Bill> findAllBillsByDateBetween(Date beginDate, Date endDate);*/

    //2) client - Consulta de facturas por rango de fechas.

    @Query(value = "SELECT u FROM Bill u WHERE (u.client.id = :idClient AND u.firstMeasurement BETWEEN :firstDate AND :lastDate) AND " +
            "(u.lastMeasurement BETWEEN :firstDate AND :lastDate)")
    Page<Bill> findAllBillsByUserAndDateBetween(Integer idClient, Date firstDate, Date lastDate, Pageable pageable);

     @Query( value= "SELECT * FROM  bills  WHERE  id_client = :idClient and pay=false" , nativeQuery = true)
    List<Bill> findByClientIdNotPay(Integer idClient);

    /** backoffice 4) Consulta de facturas impagas por cliente y domicilio.**/

    @Query(value= "SELECT * FROM addresses a\n" +
            "INNER JOIN clients c ON c.id = a.id_client\n" +
            "INNER JOIN bills b ON b.id_client = c.id\n" +
            "WHERE b.pay = FALSE AND c.id = :idClient AND a.id_address = :idAddress AND b.id_address = a.id_address\n" +
            "GROUP BY b.id_bill;", nativeQuery = true)
    Page<Bill>findUnpaidBillsByClientIdAndAddressId(Integer idClient, Integer idAddress, Pageable pageable);

}
