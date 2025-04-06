package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ServiceCategoryImpl implements CategoryService{

    private List<Category> categories = new ArrayList<>();

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
            categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories.stream()
                .filter(e -> Objects.equals(e.getCategoryId(), categoryId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Category with ID: " + categoryId + " not found!"));

        categories.remove(category);

        return "Category deleted successfully!";
    }

    @Override
    public Category updateCategory(Category updatedCategory, Long categoryId) {

        Category category = categories.stream()
                .filter(e -> Objects.equals(e.getCategoryId(), categoryId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Error: Category with ID: " + categoryId + " not found!"));

        category.setCategoryName(updatedCategory.getCategoryName());

        return category;


    }
}
