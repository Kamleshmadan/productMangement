package com.example.product.management.service;

import com.example.product.management.entity.Products;
import com.example.product.management.exception.ProductNotFoundException;
import com.example.product.management.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;


    public List<Products> getAllProducts() {
        List<Products> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            throw new ProductNotFoundException("No Product Found!!!");
        }
        return productList;
    }

    public Products getProductById(int id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("product not found for Id: " + id));
    }

    public Products addProduct(Products product) {
        return productRepository.save(product);
    }

    public Products updateProduct(Products product) {
        Products existingProduct = getProductById(product.getId());
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setEnable(product.isEnable());
        return productRepository.save(existingProduct);
    }

    public Products updatePriceQuantity(int id, double newPrice, int newQuantity) {
        Products existingProduct = getProductById(id);
        existingProduct.setPrice(newPrice);
        existingProduct.setQuantity(newQuantity);
        return productRepository.save(existingProduct);
    }

    public Products updateVisibility(int id, boolean isVisible) {
        Products existingProduct = getProductById(id);
        existingProduct.setEnable(isVisible);
        return productRepository.save(existingProduct);
    }

}
