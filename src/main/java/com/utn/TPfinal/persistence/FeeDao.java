package com.utn.TPfinal.persistence;

import com.utn.TPfinal.domain.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeDao extends JpaRepository<Fee, Integer> {
}
