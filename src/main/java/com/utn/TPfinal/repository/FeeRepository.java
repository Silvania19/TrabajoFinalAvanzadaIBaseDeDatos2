package com.utn.TPfinal.repository;

import com.utn.TPfinal.domain.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Integer> {
}
