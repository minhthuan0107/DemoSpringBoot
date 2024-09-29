package com.example.springweb.repository;
import com.example.springweb.entity.Category;
import com.example.springweb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductName(String productName);

    List<Product> findByCategory(Category category);
}
