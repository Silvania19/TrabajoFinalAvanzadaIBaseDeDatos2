package com.utn.TPfinal.repository;

import com.utn.TPfinal.domain.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    /*@Query(value = "SELECT * FROM bills b " +
            "WHERE (b.first_measurement BETWEEN :beginDate AND :endDate) OR " +
            "(b.last_measurement BETWEEN :beginDate AND :endDate);"
            , nativeQuery = true)
    List<Bill> findAllBillsByDateBetween(Date beginDate, Date endDate);*/

    //2) Consulta de facturas por rango de fechas.
    //TODO implementar page
    @Query(value = "SELECT u FROM Bill u WHERE (u.firstMeasurement BETWEEN :firstDate AND :lastDate) AND " +
            "(u.lastMeasurement BETWEEN :firstDate AND :lastDate)")
    List<Bill> findAllBillsByDateBetween(Date firstDate, Date lastDate);

<<<<<<< HEAD
   /* @Query( value= "SELECT * FROM  bills  WHERE  id_client = :idClient" , nativeQuery = true)*/
   /* @Query(value = "select b from Bill b where (b.client = :idClient)")*/
    List<Bill>findAllByClientId(Integer idClient);


=======
    //List<Bill> findAllByClient
>>>>>>> c9af0d185f72c722b4cdd060bd2f0e0bb8b3a487
}
