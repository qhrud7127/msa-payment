package com.vtw.dna.order.domain;

import com.vtw.dna.product.domain.Products;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Orders {

    @Id
    @GeneratedValue
    private Long id;

    private String tid;

    private String status;

    private String userId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Products products;

    private Long quantity;

    private LocalDateTime orderDateTime;
}
