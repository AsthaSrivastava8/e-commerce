package com.ecommerce.project.service;

import com.ecommerce.project.controller.dto.CategoryDTO;
import com.ecommerce.project.controller.dto.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories();

    CategoryDTO createCategory(CategoryDTO category);

    String deleteCategory(Long categoryId);

    String updateCategory(CategoryDTO category, Long categoryId);
}
