package com.example.product.management.controller;

import com.example.product.management.entity.Products;
import com.example.product.management.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@NoArgsConstructor
@AllArgsConstructor
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<Products> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Products getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Products> addProduct(@Valid @RequestBody Products product) {
        Products created = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/updateProduct")
    public Products updateProduct(@Valid @RequestBody Products product) {
        return productService.updateProduct(product);
    }

    @PatchMapping("/{id}/price-quantity")
    public Products updatePriceQuantity(
            @PathVariable int id,
            @RequestParam double newPrice,
            @RequestParam int newQuantity
    ) {
        return productService.updatePriceQuantity(id, newPrice, newQuantity);
    }

    @PatchMapping("/{id}/visibility")
    public Products updateVisibility(
            @PathVariable int id,
            @RequestParam boolean isVisible
    ) {
        return productService.updateVisibility(id, isVisible);
    }
}

