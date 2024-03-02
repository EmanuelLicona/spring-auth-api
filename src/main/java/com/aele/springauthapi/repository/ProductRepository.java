package com.aele.springauthapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aele.springauthapi.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
