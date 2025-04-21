package com.ecommerce.project.service;

import com.ecommerce.project.dto.CategoryDTO;
import com.ecommerce.project.dto.CategoryResponse;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.entity.Category;
import com.ecommerce.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
    public CategoryResponse getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No categories found!");
        }

        List<CategoryDTO> categoriesDTO = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());

        long totalElements = categoryPage.getTotalElements();
        int totalPages = categoryPage.getTotalPages();
        boolean isLastPage = categoryPage.isLast();

        CategoryResponse categoryResponse = new CategoryResponse(categoriesDTO, pageNumber, pageSize,
                totalElements, totalPages, isLastPage);
        return categoryResponse;
    }

    @Override
    public CategoryResponse createCategory(CategoryDTO category) {
        Category foundCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (foundCategory != null) {
            throw new APIException("Category with name " + category.getCategoryName() + " already exists!");
        }

        categoryRepository.save(modelMapper.map(category, Category.class));

        return new CategoryResponse(Collections.singletonList(modelMapper.map(category, CategoryDTO.class)));
    }

    @Override
    public String createCategories(List<CategoryDTO> categories) {

        for (CategoryDTO categoryDTO : categories) {

            Category foundCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
            if (foundCategory != null) {
                throw new APIException("Category with name " + categoryDTO.getCategoryName() + " already exists!");
            }
        }

        for (CategoryDTO categoryDTO : categories) {

            categoryRepository.save(modelMapper.map(categoryDTO, Category.class));
        }

        return categories.size() + " categories created successfully!";
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            categoryRepository.deleteById(categoryId);

        } else {
            throw new ResourceNotFoundException("Category", "Category ID", categoryId);
        }

        return "Category with ID: " + category.get().getCategoryId() + " deleted successfully!";
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
