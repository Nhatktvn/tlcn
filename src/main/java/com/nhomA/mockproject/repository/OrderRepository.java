package com.nhomA.mockproject.repository;

import com.nhomA.mockproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
