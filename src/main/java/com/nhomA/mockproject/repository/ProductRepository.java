package com.nhomA.mockproject.repository;

import com.nhomA.mockproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String nameProduct);
}
