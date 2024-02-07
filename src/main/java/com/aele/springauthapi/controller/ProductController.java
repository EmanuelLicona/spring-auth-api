package com.aele.springauthapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aele.springauthapi.entity.ProductEntity;
import com.aele.springauthapi.repository.ProductRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> findAll() {
        List<ProductEntity> products = productRepository.findAll();

        if (products != null && !products.isEmpty()) {
            return ResponseEntity.ok(products);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductEntity> postMethodName(@RequestBody @Valid ProductEntity product) {

        // ! Si no logra guardar un producto se lanza una excepcion
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(product));
    }

}
