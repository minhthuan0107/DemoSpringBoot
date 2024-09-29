package com.example.springweb.controller;

import com.example.springweb.entity.Category;
import com.example.springweb.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/api/category")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCategory(){
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse("No categories found!", null));
        }
        return ResponseEntity.ok(new ApiResponse("Categories retrieved successfully!",categories));
    }
    @GetMapping("{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById (@PathVariable Long categoryId){
        if (!categoryRepository.existsById(categoryId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Category not found!", null));
        }
        Category category = categoryRepository.findById(categoryId).get();
        return ResponseEntity.ok(new ApiResponse("Category found successfully!", category));
    }
}
