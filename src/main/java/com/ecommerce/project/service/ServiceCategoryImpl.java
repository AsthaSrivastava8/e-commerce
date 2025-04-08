package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRepository;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class ServiceCategoryImpl implements CategoryService{

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
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findAll().stream()
                .filter(e -> Objects.equals(e.getCategoryId(), categoryId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Category with ID: " + categoryId + " not found!"));

        categoryRepository.delete(category);

        return "Category deleted successfully!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Category updatedCategory = categoryRepository.findAll().stream()
                .filter(e -> Objects.equals(e.getCategoryId(), categoryId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Error: Category with ID: " + categoryId + " not found!"));

        updatedCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(updatedCategory);

        return category;


    }
}
