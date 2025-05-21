package com.technova.shopverse.repository;
import com.technova.shopverse.dto.ProductDTO;
import com.technova.shopverse.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
}


