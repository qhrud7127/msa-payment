package com.vtw.dna.product.controller;

import com.vtw.dna.product.domain.Products;
import com.vtw.dna.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products/list")
    public List<Products> getProducts() {
        List<Products> list = productService.findAll();
        return list;
    }

    @PostMapping("/products/create")
    public Products createProduct(@RequestBody Products product) {
        return productService.create(product);
    }

    @PostMapping("/products/update")
    public Products updateProduct(@RequestBody Products product) {
        return productService.update(product);
    }

    @GetMapping("/products/delete")
    public Long deleteProduct(@RequestParam Long id) {
        productService.delete(id);
        return id;
    }
}
