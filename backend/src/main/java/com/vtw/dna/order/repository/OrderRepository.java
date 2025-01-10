package com.vtw.dna.order.repository;

import com.vtw.dna.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Orders findByTid(String tid);
}
