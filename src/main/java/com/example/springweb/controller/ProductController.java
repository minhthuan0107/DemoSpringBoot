package com.example.springweb.controller;
import com.example.springweb.entity.Category;
import com.example.springweb.entity.Product;
import com.example.springweb.entity.ProductImage;
import com.example.springweb.repository.CategoryRepository;
import com.example.springweb.repository.ProductImageRepository;
import com.example.springweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("v1/api/products")
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductImageRepository productImageRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/category/{categoryId}")
    public  ResponseEntity<ApiResponse> getProductsByCategoryId (@PathVariable Long categoryId){
        if (!categoryRepository.existsById(categoryId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Category not found!", null));
        }
         Category category = categoryRepository.findById(categoryId).get();
         List<Product> products = productRepository.findByCategory(category);
        return ResponseEntity.ok(new ApiResponse("Products retrieved successfully", products));

    }
    @GetMapping("{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        if (!productRepository.existsById(productId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Product not found!", null));
        }
        Product product = productRepository.findById(productId).get();
        return ResponseEntity.ok(new ApiResponse("Product found successfully!", product));
    }
    @PostMapping("")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody Product product) {
        if (productRepository.existsByProductName(product.getProductName())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ApiResponse("Product with this name already exists!",null));
        }
        Product savedProduct = productRepository.save(product);
        // Lưu danh sách ProductImage sau khi thiết lập product cho mỗi image
        List<ProductImage> productImages = product.getProductImages().stream()
                .map(image -> {
                    image.setProduct(savedProduct); // Gán product đã lưu vào mỗi ProductImage
                    return image;
                })
                .collect(Collectors.toList());
        // Lưu danh sách ProductImage
        productImageRepository.saveAll(productImages);
        return ResponseEntity.ok(new ApiResponse("Product has been added successfully!", savedProduct));
    }
}
