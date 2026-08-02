package com.bharat.online_certificate_verification_system.exception;

import com.bharat.online_certificate_verification_system.dto.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceAlreadyExists(
            ResourceAlreadyExistsException ex,
            HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex) {

           Map<String, String> errors = new HashMap<>();

           ex.getBindingResult().getFieldErrors().forEach(error->
                   errors.put(error.getField(),error.getDefaultMessage())
           );
        return ResponseEntity.badRequest().body(errors);
    }
}