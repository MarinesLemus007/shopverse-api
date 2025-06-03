package com.technova.shopverse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String name;
    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;
    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Double price;

    //Relación mucho a uno con Category
    @NotNull(message = "La categoría es obligatoria")
    @ManyToOne
    @JoinColumn(name = "category_id") // Esta será la clave foránea en la base de datos
    private Category category;

    public Product() {}

    public Product(String name, String description, Double price, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }



    // Getters y Setters


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
}