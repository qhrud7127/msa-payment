package com.vtw.dna.product.controller;

import com.vtw.dna.product.domain.Products;
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
    public List<Products> getProducts() {
        List<Products> list = productService.findAll();
        return list;
    }


    @PostMapping("/products")
    public Products addProduct(@RequestBody Products product) {
        return productService.create(product);
    }

}
