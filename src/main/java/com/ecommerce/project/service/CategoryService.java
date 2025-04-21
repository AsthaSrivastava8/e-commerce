package com.ecommerce.project.service;

import com.ecommerce.project.controller.dto.CategoryDTO;
import com.ecommerce.project.controller.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse getAllCategories(int pageNumber, int pageSize);

    CategoryDTO createCategory(CategoryDTO category);

    String createCategories(List<CategoryDTO> categories);

    CategoryDTO deleteCategory(Long categoryId);

    String updateCategory(CategoryDTO category, Long categoryId);
}
