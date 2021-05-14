package com.utn.TPfinal.persistence;

import com.utn.TPfinal.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelDao extends JpaRepository<Model, Integer> {
}
