package com.utn.TPfinal.repository;

import com.utn.TPfinal.domain.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterRepository extends JpaRepository<Meter, Integer> {
    Meter findBySerialNumberAndPasswordMeter(String serialNumber, String password);

    Meter findBySerialNumber(String serialNumber);

   //@Query(value = "delete from meters where serial_number= ?serialNumber", nativeQuery = true)
    void deleteBySerialNumber(String serialNumber);

    void removeBySerialNumber(String serialNumber);
}
