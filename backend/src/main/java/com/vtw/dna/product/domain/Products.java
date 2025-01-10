package com.vtw.dna.product.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Products {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String type;

    private Long price;
}
