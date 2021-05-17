package com.utn.TPfinal.persistence;

import com.utn.TPfinal.domain.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandDao extends JpaRepository<Brands, Integer> {
}
