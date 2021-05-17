package com.utn.TPfinal.persistence;

import com.utn.TPfinal.domain.Measuring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasuringDao extends JpaRepository<Measuring, Integer> {
}
