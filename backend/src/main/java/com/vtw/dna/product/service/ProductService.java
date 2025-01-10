package com.vtw.dna.product.service;

import com.vtw.dna.product.domain.Products;
import com.vtw.dna.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<Products> findAll() {
        return productRepository.findAll();
    }

    public Products create(Products product) {
        return productRepository.save(product);
    }

    public Products update(Products product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
