package com.aele.springauthapi.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null // Cuando el cliente envia el objeto este debe ser nulo por que es generado
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;
    
    @DecimalMin(value = "0.01")
    private BigDecimal price;
}
