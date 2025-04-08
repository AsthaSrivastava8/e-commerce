package com.ecommerce.project.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class APIException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public APIException(String message) {
        super(message);
    }

}
