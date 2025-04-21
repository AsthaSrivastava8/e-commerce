package com.ecommerce.project.service;

import com.ecommerce.project.dto.CategoryDTO;
import com.ecommerce.project.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);

    CategoryResponse createCategory(CategoryDTO category);

    String createCategories(List<CategoryDTO> categories);

    String deleteCategory(Long categoryId);

    String updateCategory(CategoryDTO category, Long categoryId);
}
