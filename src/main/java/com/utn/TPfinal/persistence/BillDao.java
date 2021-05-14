package com.utn.TPfinal.persistence;

import com.utn.TPfinal.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDao extends JpaRepository<Bill, Integer> {
}
