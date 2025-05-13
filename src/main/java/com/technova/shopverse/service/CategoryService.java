package com.technova.shopverse.service;

import com.technova.shopverse.model.Category;
import com.technova.shopverse.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    public Category createCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio.");
        }
        if (category.getDescription() == null || category.getDescription().length() < 10) {
            throw new IllegalArgumentException("La descripción debe tener al menos 10 caracteres.");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updated) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
        }
        Category category = optionalCategory.get();
        category.setName(updated.getName());
        category.setDescription(updated.getDescription());
        return categoryRepository.save(category);
    }
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }


}
