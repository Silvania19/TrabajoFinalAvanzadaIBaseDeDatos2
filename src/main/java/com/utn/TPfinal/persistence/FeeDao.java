package com.utn.TPfinal.persistence;

import com.utn.TPfinal.domain.Fees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeDao extends JpaRepository<Fees, Integer> {
}