package com.nhomA.mockproject.repository;

import com.nhomA.mockproject.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String nameProduct);
    List<Product> findByPriceBetween(double minPrice, double maxPrice);
    Page<Product> findByCategoryId(Long idCategory, Pageable pageable);
    List<Product> findByCategoryId(Long idCategory);
}
