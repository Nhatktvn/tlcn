package com.nhomA.mockproject.mapper.impl;

import com.nhomA.mockproject.dto.ProductRequestDTO;
import com.nhomA.mockproject.dto.ProductResponseDTO;
import com.nhomA.mockproject.entity.Product;
import com.nhomA.mockproject.entity.Reviews;
import com.nhomA.mockproject.mapper.ProductMapper;
import com.nhomA.mockproject.mapper.ReviewMapper;
import com.nhomA.mockproject.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapperImpl implements ProductMapper {
    private final UserMapper userMapper;
    public ProductMapperImpl(UserMapper userMapper, ReviewMapper reviewMapper) {
        this.userMapper = userMapper;
        this.reviewMapper = reviewMapper;
    }
    private  final ReviewMapper reviewMapper;

    @Override
    public Product toEntity(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setAvailable(productRequestDTO.getAvailable());
        product.setPrice(productRequestDTO.getPrice());
        product.setDiscount(productRequestDTO.getDiscount());
        product.setUrlImage(productRequestDTO.getUrlImage());
        product.setDescription(productRequestDTO.getDescription());
        return product;
    }

    @Override
    public ProductResponseDTO toResponseDTO(Product product) {
       ProductResponseDTO productResponseDTO = new ProductResponseDTO();
       productResponseDTO.setId(product.getId());
       productResponseDTO.setName(product.getName());
       productResponseDTO.setCategory_id(product.getCategory().getId());
       productResponseDTO.setAvailable(product.getAvailable());
       productResponseDTO.setDiscount(product.getDiscount());
       productResponseDTO.setPrice(product.getPrice());
       productResponseDTO.setUrlImage(product.getUrlImage());
       productResponseDTO.setCreatedDate(product.getCreatedDate());
       productResponseDTO.setUpdatedDate(product.getUpdatedDate());
       productResponseDTO.setUserCreated(userMapper.toDTO(product.getUserCreated()));
       productResponseDTO.setUserUpdated(userMapper.toDTO(product.getUserUpdated()));
       productResponseDTO.setDescription(product.getDescription());
       double countRate = 0;
        if (product.getReviews() != null){
            productResponseDTO.setReviews(reviewMapper.toDTOs(product.getReviews()));
            for(Reviews rv: product.getReviews()){
                countRate = countRate + rv.getRate();
            }
            countRate = countRate / product.getReviews().size();
        }
        productResponseDTO.setRate(countRate);
        return productResponseDTO;
    }

    @Override
    public List<ProductResponseDTO> toResponseDTOs(List<Product> products) {
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        for(Product p: products){
            productResponseDTOS.add(this.toResponseDTO(p));
        }
        return productResponseDTOS;
    }
}
