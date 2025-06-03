package com.technova.shopverse.controller;
import com.technova.shopverse.dto.CategoryDTO;
import com.technova.shopverse.model.Category;
import com.technova.shopverse.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/categories")
@Tag(name = "Categorias", description = "Operaciones relacionadas con las categorias")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @Operation(
            summary = "Obtener las categorias según id",
            description = "Este endpoint devuelve la categoria disponible según el id"
    )
    @ApiResponse(responseCode = "200", description = "Lista de categorias retornadas correctamente")
    @GetMapping("/{id}/details")
    public ResponseEntity<CategoryDTO> getCategoryDetails(@PathVariable Long id) {
        try {
            CategoryDTO dto = categoryService.getCategoryDTOById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.ok(categories); // 200
        }
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Category category) {
        try {
            Category created = categoryService.createCategory(category);
            return ResponseEntity.status(201).body(created); // 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id, @RequestBody Category category) {
        try {
            Category updated = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(updated); // 200 OK
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("no encontrada")) {
                return ResponseEntity.notFound().build(); // 404 Not Found
            } else {
                return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request
            }
        }
    }

    // Eliminar una categoría por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}