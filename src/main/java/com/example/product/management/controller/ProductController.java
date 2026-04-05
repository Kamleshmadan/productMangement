package com.example.product.management.controller;

import com.example.product.management.entity.Products;
import com.example.product.management.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@NoArgsConstructor
@AllArgsConstructor
public class ProductController {

    @Autowired
    ProductService productService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public List<Products> getAllProducts() {
        return productService.getAllProducts();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public Products getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addProduct")
    public ResponseEntity<Products> addProduct(@Valid @RequestBody Products product) {
        Products created = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateProduct")
    public Products updateProduct(@Valid @RequestBody Products product) {
        return productService.updateProduct(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/price-quantity")
    public Products updatePriceQuantity(
            @PathVariable int id,
            @RequestParam double newPrice,
            @RequestParam int newQuantity
    ) {
        return productService.updatePriceQuantity(id, newPrice, newQuantity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/visibility")
    public Products updateVisibility(
            @PathVariable int id,
            @RequestParam boolean isVisible
    ) {
        return productService.updateVisibility(id, isVisible);
    }
}

