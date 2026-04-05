package com.example.product.management.controller;

import com.example.product.management.entity.Cart;
import com.example.product.management.entity.CartItems;
import com.example.product.management.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/addItem")
    public ResponseEntity<Cart> addToCart(
            @RequestParam String userName,
            @RequestParam int productId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.addToCart(userName, productId, quantity));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/updateItems")
    public CartItems updateCartItem(
            @RequestParam String userName,
            @RequestParam int productId,
            @RequestParam int quantity
    ) {
        return cartService.updateCartItem(userName, productId, quantity);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/removeItems")
    public ResponseEntity<String> removeCartItem(
            @RequestParam String userName,
            @RequestParam int productId
    ) {
        cartService.removeCartItem(userName, productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Removed product Id: " + productId + " for userName: " + userName);
    }
}

