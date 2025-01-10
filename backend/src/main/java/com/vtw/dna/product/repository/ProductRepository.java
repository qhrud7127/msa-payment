package com.vtw.dna.product.repository;

import com.vtw.dna.product.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {
}
