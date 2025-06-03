package com.technova.shopverse.controller;

import org.springframework.http.ResponseEntity;
import com.technova.shopverse.dto.ProductDTO;
import com.technova.shopverse.model.Product;
import com.technova.shopverse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/products")
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductController {

    // Inyectamos el repositorio con @Autowired
    @Autowired
    private ProductService productService;

    @Operation(
            summary = "Obtener todos los productos",
            description = "Este endpoint devuelve una lista con todos los productos disponibles"
    )
    @ApiResponse(responseCode = "200", description = "Lista de productos retornada correctamente")
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.ok(products); // 200 OK
        }
    }
    @GetMapping("/dto")
    public ResponseEntity<List<ProductDTO>> getAllWithCategory() {
        List<ProductDTO> dtoList = productService.getAllProductDTOs();
        if (dtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product) {
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

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.getByCategoryId(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
}