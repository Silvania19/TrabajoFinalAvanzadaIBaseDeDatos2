package com.utn.TPfinal.persistence;

import com.utn.TPfinal.domain.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterDao extends JpaRepository<Meter, Integer> {
}
