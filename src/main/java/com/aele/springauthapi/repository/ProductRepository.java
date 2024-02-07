package com.aele.springauthapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aele.springauthapi.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
