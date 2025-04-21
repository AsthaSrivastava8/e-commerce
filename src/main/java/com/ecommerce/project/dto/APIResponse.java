package com.ecommerce.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class APIResponse {

    private String message;
    private boolean status;
}
