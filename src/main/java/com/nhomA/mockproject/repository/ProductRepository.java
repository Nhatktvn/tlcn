package com.nhomA.mockproject.repository;

import com.nhomA.mockproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
