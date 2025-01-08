package com.vtw.dna.product.controller;

import com.vtw.dna.product.domain.Product;
import com.vtw.dna.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public List<Product> getProducts() {
        List<Product> list = productService.findAll();
        return list;
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        return productService.create(product);
    }

}
