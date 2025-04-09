package com.ecommerce.project.service;

import com.ecommerce.project.controller.dto.CategoryDTO;
import com.ecommerce.project.controller.dto.CategoryResponse;
import com.ecommerce.project.entity.Category;

public interface CategoryService {

    CategoryResponse getAllCategories();

    CategoryDTO createCategory(CategoryDTO category);

    String deleteCategory(Long categoryId);

    String updateCategory(Category category, Long categoryId);
}
