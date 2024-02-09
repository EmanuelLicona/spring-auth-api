package com.aele.springauthapi.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aele.springauthapi.entity.ProductEntity;
import com.aele.springauthapi.repository.ProductRepository;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
    @GetMapping
    public ResponseEntity<List<ProductEntity>> findAll() {
        List<ProductEntity> products = productRepository.findAll();

        if (products != null && !products.isEmpty()) {
            return ResponseEntity.ok(products);
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('SAVE_ONE_PRODUCT')")
    @PostMapping
    public ResponseEntity<ProductEntity> postMethodName(@RequestBody @Valid ProductEntity product) {

        // ! Si no logra guardar un producto se lanza una excepcion
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(product));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception exception, HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("message", exception.getLocalizedMessage());
        error.put("class", exception.getClass().getName());
        error.put("timestamp", new Date().toString());
        error.put("path", request.getRequestURI());
        error.put("http-method", request.getMethod());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (exception instanceof AccessDeniedException) {

            status = HttpStatus.FORBIDDEN;
        }

        System.out.println("Error: " + status);

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleGeneriException(Exception exception, HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("message", exception.getLocalizedMessage());
        error.put("class", exception.getClass().getName());
        error.put("timestamp", new Date().toString());
        error.put("path", request.getRequestURI());
        error.put("http-method", request.getMethod());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (exception instanceof AccessDeniedException) {

            status = HttpStatus.FORBIDDEN;
        }

        System.out.println("Error: " + status);

        return ResponseEntity.status(status).body(error);
    }


    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwtException(Exception exception, HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("message", exception.getLocalizedMessage());
        error.put("class", exception.getClass().getName());
        error.put("timestamp", new Date().toString());
        error.put("path", request.getRequestURI());
        error.put("http-method", request.getMethod());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (exception instanceof AccessDeniedException) {

            status = HttpStatus.FORBIDDEN;
        }

        System.out.println("Error: " + status);

        return ResponseEntity.status(status).body(error);
    }

}
