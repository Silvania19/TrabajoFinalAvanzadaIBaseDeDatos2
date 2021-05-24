package com.utn.TPfinal.repository;

import com.utn.TPfinal.domain.Measuring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasuringRepository extends JpaRepository<Measuring, Integer> {
}
