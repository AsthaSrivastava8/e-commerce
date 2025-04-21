package com.ecommerce.project.service;

import com.ecommerce.project.controller.dto.CategoryDTO;
import com.ecommerce.project.controller.dto.CategoryResponse;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.entity.Category;
import com.ecommerce.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceCategoryImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public ServiceCategoryImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryResponse getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No categories found!");
        }

        List<CategoryDTO> categoriesDTO = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());

        CategoryResponse categoryResponse = new CategoryResponse(categoriesDTO);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO category) {
        Category foundCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (foundCategory != null) {
            throw new APIException("Category with name " + category.getCategoryName() + " already exists!");
        }

        categoryRepository.save(modelMapper.map(category, Category.class));

        return category;
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            categoryRepository.deleteById(categoryId);

        } else {
            throw new ResourceNotFoundException("Category", "Category ID", categoryId);
        }

        return "Category deleted successfully!";
    }

    @Override
    public String updateCategory(CategoryDTO category, Long categoryId) {

        Optional<Category> updatedCategory = categoryRepository.findById(categoryId);

        if (updatedCategory.isPresent()) {
            Category updateCategory = updatedCategory.get();
            updateCategory.setCategoryName(category.getCategoryName());
            categoryRepository.save(updateCategory);

        } else {
            throw new ResourceNotFoundException("Category", "Category ID", categoryId);
        }

        return "Category updated successfully!";

    }
}
