package com.utn.TPfinal.repository;

import com.utn.TPfinal.domain.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterRepository extends JpaRepository<Meter, Integer> {
    Meter findBySerialNumberAndPasswordMeter(String serialNumber, String password);
}
