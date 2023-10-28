package com.nhomA.mockproject.service;

import com.nhomA.mockproject.dto.ProductRequestDTO;
import com.nhomA.mockproject.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO getProductById (Long id);
    List<ProductResponseDTO> getAllProduct(int pageNo, int pageSize, String sortBy, String sortDir);
    ProductResponseDTO createProduct (String username, ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProductById (String uysername,Long id, ProductRequestDTO productRequestDTO);
    Boolean deleteProductById(Long id);
    List<ProductResponseDTO> getProductsByCategory (Long id);
    List<ProductResponseDTO> searchProduct (String searchName);
}
