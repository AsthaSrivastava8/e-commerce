package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.dto.CategoryDTO;
import com.ecommerce.project.dto.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private final CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name = "pageNumber",
                                                                     defaultValue = AppConstants.PAGE_NUMBER,
                                                                     required = false)
                                                             int pageNumber,
                                                             @RequestParam(name = "pageSize",
                                                                     defaultValue = AppConstants.PAGE_SIZE,
                                                                     required = false)
                                                             int pageSize,
                                                             @RequestParam(name = "sortBy",
                                                                     defaultValue = AppConstants.SORT_CATEGORIES_BY,
                                                                     required = false)
                                                             String sortBy,
                                                             @RequestParam(name = "sortOrder",
                                                                     defaultValue = AppConstants.SORT_ORDER,
                                                                     required = false)
                                                             String sortOrder) {

        return ResponseEntity.ok(categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder));
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryDTO category) {

        categoryService.createCategory(category);
        return ResponseEntity.ok(HttpStatus.CREATED.getReasonPhrase());
    }

    @PostMapping("/admin/categories/multiple")
    public ResponseEntity<String> addMultipleCategories(@Valid @RequestBody List<CategoryDTO> categories) {

        categoryService.createCategories(categories);
        return ResponseEntity.ok(HttpStatus.CREATED.getReasonPhrase());
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {

        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDTO category, @PathVariable Long categoryId) {

        return ResponseEntity.ok(categoryService.updateCategory(category, categoryId));

    }
}
