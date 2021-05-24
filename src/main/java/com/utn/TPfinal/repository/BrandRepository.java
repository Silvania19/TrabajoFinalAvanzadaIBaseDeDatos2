package com.utn.TPfinal.repository;

import com.utn.TPfinal.domain.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brands, Integer> {
}
