package com.nhomA.mockproject.service.impl;

import com.nhomA.mockproject.dto.CategoryDTO;
import com.nhomA.mockproject.entity.Category;
import com.nhomA.mockproject.exception.CategoryNotFoundException;
import com.nhomA.mockproject.mapper.CategoryMapper;
import com.nhomA.mockproject.repository.CategoryRepository;
import com.nhomA.mockproject.service.CategoryService;
import com.nhomA.mockproject.util.PaginationAndSortingUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }
    @Transactional
    @Override
    public CategoryDTO getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return categoryMapper.toDTO(category.get());
    }

    @Transactional
    @Override
    public List<CategoryDTO> getAllCategory(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable= PaginationAndSortingUtils.getPageable(pageNo,pageSize,sortBy,sortDir);
        Page<Category> categories= categoryRepository.findAll(pageable);
        List<Category> categoriesContent = categories.getContent();
        return categoryMapper.toDTOs(categoriesContent);
    }
    @Transactional
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        categoryRepository.save(category);
        return categoryDTO;
    }
    @Transactional
    @Override
    public CategoryDTO updateCategoryById(Long id, CategoryDTO categoryDTO) {
        Optional<Category> existedCategory = categoryRepository.findById(id);
        if(existedCategory.isEmpty()){
            throw new CategoryNotFoundException("Category not found!") ;
        }
        Category category = existedCategory.get();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setUrlImage(categoryDTO.getUrlImage());
        categoryRepository.save(category);
    return categoryDTO;
    }
    @Transactional
    @Override
    public Boolean deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
        return true;
    }
}
