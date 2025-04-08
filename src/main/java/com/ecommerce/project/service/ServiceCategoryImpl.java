package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCategoryImpl implements CategoryService {

    CategoryRepository categoryRepository;

    public ServiceCategoryImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        Category foundCategory = categoryRepository.findCategoryByCategoryName(category.getCategoryName());
        if(foundCategory != null) {
            throw new APIException("Category already exists!");
        }
        categoryRepository.save(category);
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
    public String updateCategory(Category category, Long categoryId) {

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
