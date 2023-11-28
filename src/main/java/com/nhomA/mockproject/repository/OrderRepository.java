package com.nhomA.mockproject.repository;

import com.nhomA.mockproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
