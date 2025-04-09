package com.ecommerce.project.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryResponse {

    private List<CategoryDTO> content;
}
