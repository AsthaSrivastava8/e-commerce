package com.ecommerce.project.exceptions;

import com.ecommerce.project.dto.APIResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<Map<String, String>> methodOrConstraintViolationException(Exception exception) {

        Map<String, String> response = new HashMap<>();

        if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {

            methodArgumentNotValidException.getBindingResult()
                    .getAllErrors().forEach((error) -> {
                        String fieldName = ((FieldError) error).getField();
                        String errorMessage = error.getDefaultMessage();
                        response.put(fieldName, errorMessage);
                    });
        } else if (exception instanceof ConstraintViolationException constraintViolationException) {

            constraintViolationException.getConstraintViolations().forEach((error) -> {
                String propertyPath = error.getPropertyPath().toString();
                String message = error.getMessage();
                response.put(propertyPath, message);

            });
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<Map<String, String>> transactionSystemExceptionException(TransactionSystemException exception) {

        Map<String, String> response = new HashMap<>();

        response.put(exception.getMessage(), exception.getCause().getCause().getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> resourceNotFoundException(ResourceNotFoundException exception) {
        return new ResponseEntity<>(new APIResponse(exception.getMessage(), false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> apiException(APIException exception) {
        return new ResponseEntity<>(new APIResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
    }
}
