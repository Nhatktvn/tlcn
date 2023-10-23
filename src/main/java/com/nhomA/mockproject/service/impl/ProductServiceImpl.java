package com.nhomA.mockproject.service.impl;

import com.nhomA.mockproject.dto.ProductRequestDTO;
import com.nhomA.mockproject.dto.ProductResponseDTO;
import com.nhomA.mockproject.entity.Category;
import com.nhomA.mockproject.entity.Product;
import com.nhomA.mockproject.entity.User;
import com.nhomA.mockproject.exception.CategoryNotFoundException;
import com.nhomA.mockproject.exception.ProductNotFoundException;
import com.nhomA.mockproject.mapper.ProductMapper;
import com.nhomA.mockproject.repository.CategoryRepository;
import com.nhomA.mockproject.repository.ProductRepository;
import com.nhomA.mockproject.repository.UserRepository;
import com.nhomA.mockproject.service.ProductService;
import com.nhomA.mockproject.util.PaginationAndSortingUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;
    private  final CategoryRepository categoryRepository;
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, ProductMapper productMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public ProductResponseDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        ProductResponseDTO productResponseDTO = productMapper.toResponseDTO(product.get());
        return productResponseDTO;
    }
    @Transactional
    @Override
    public List<ProductResponseDTO> getAllProduct(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable= PaginationAndSortingUtils.getPageable(pageNo,pageSize,sortBy,sortDir);
        Page<Product> products= productRepository.findAll(pageable);
        List<Product> productsContent = products.getContent();
        return productMapper.toResponseDTOs(productsContent);
    }
    @Transactional
    @Override
    public ProductResponseDTO createProduct(String username, ProductRequestDTO productRequestDTO) {
        Optional<User> existedUser = userRepository.findByUsername(username);
        User user = existedUser.get();
        Product product = productMapper.toEntity(productRequestDTO);
        Optional<Category> existedCategory = categoryRepository.findById(productRequestDTO.getCategory_id());
        if(existedCategory.isEmpty()){
            throw new CategoryNotFoundException("Category not found!");
        }
        Category category = existedCategory.get();
        category.getProducts().add(product);
        product.setCategory(category);
        product.setUserCreated(user);
        product.setUserUpdated(user);
        product.setCreatedDate(ZonedDateTime.now());
        product.setUpdatedDate(ZonedDateTime.now());
        Product saveProduct = productRepository.save(product);
        return productMapper.toResponseDTO(saveProduct);
    }
    @Transactional
    @Override
    public ProductResponseDTO updateProductById(String username, Long id, ProductRequestDTO productRequestDTO){
        User userUpdated = userRepository.findByUsername(username).get();
        Optional<Product> existedProduct = productRepository.findById(id);
        if(existedProduct.isEmpty()){
            throw new ProductNotFoundException("Product not found!");
        }
        Optional<Category> existedCategory = categoryRepository.findById(productRequestDTO.getCategory_id());
        if(existedProduct.isEmpty()){
            throw new CategoryNotFoundException("Category not found!");
        }
        Product product = existedProduct.get();
        product.setName(productRequestDTO.getName());
        product.setAvailable(productRequestDTO.getAvailable());
        product.setDiscount(productRequestDTO.getDiscount());
        product.setPrice(productRequestDTO.getPrice());
        product.setUrlImage(productRequestDTO.getUrlImage());
        Category category = existedCategory.get();
        product.setCategory(category);
        product.setUserUpdated(userUpdated);
        product.setUpdatedDate(ZonedDateTime.now());
        Product saveProduct = productRepository.save(product);
        return productMapper.toResponseDTO(saveProduct);
    }
    @Transactional
    @Override
    public Boolean deleteProductById(Long id) {
        productRepository.deleteById(id);
        return true;
    }
    @Transactional
    @Override
    public List<ProductResponseDTO> getProductsByCategory(Long idCategory) {
        Optional<Category> existedCategory = categoryRepository.findById(idCategory);
        if(existedCategory.isEmpty()){
            throw new CategoryNotFoundException("Category not found!");
        }
        Category category = existedCategory.get();
        return productMapper.toResponseDTOs(category.getProducts());
    }
}
