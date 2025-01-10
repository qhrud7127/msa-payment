package com.vtw.dna.product.service;

import com.vtw.dna.kakaopay.domain.ApproveRequest;
import com.vtw.dna.kakaopay.domain.ReadyRequest;
import com.vtw.dna.kakaopay.domain.ReadyResponse;
import com.vtw.dna.order.repository.OrderRepository;
import com.vtw.dna.product.domain.Products;
import com.vtw.dna.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

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
}
