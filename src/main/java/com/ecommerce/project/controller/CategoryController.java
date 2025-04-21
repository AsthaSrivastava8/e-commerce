package com.ecommerce.project.controller;

import com.ecommerce.project.controller.dto.CategoryDTO;
import com.ecommerce.project.controller.dto.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private final CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name = "pageNumber") int pageNumber,
                                                             @RequestParam(name = "pageSize") int pageSize) {

        return new ResponseEntity<>(categoryService.getAllCategories(pageNumber, pageSize), HttpStatus.OK);
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
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {

        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDTO category, @PathVariable Long categoryId) {

        try {
            return ResponseEntity.ok(categoryService.updateCategory(category, categoryId));

        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
        }
    }
}
