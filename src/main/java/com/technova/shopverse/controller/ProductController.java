package com.technova.shopverse.controller;

import org.springframework.http.ResponseEntity;
import com.technova.shopverse.model.Product;
import com.technova.shopverse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Inyectamos el repositorio con @Autowired
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.ok(products); // 200 OK
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product) {
        try {
            Product created = productService.createProduct(product);
            return ResponseEntity.status(201).body(created); // 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updated = productService.updateProduct(id, product);
            return ResponseEntity.ok(updated); // 200 OK
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.notFound().build(); // 404
            } else {
                return ResponseEntity.badRequest().body(e.getMessage()); // 400
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}