package com.nhomA.mockproject.service;

import com.nhomA.mockproject.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO getCategoryById (Long id);
    List<CategoryDTO> getAllCategory(int pageNo, int pageSize, String sortBy, String sortDir);
   CategoryDTO createCategory (CategoryDTO categoryDTO);
   CategoryDTO updateCategoryById (Long id, CategoryDTO categoryDTO);
   Boolean deleteCategoryById(Long id);
}
