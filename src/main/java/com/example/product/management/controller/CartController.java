package com.example.product.management.controller;

import com.example.product.management.entity.Cart;
import com.example.product.management.entity.CartItems;
import com.example.product.management.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/addItem")
    public ResponseEntity<Cart> addToCart(
            @RequestParam String userName,
            @RequestParam int productId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.addToCart(userName, productId, quantity));
    }

    List<Integer> list=new ArrayList<>();
    @PutMapping("/updateItems")
    public CartItems updateCartItem(
            @RequestParam String userName,
            @RequestParam int productId,
            @RequestParam int quantity
    ) {
        return cartService.updateCartItem(userName, productId, quantity);
    }

    @DeleteMapping("/removeItems")
    public ResponseEntity<String> removeCartItem(
            @RequestParam String userName,
            @RequestParam int productId
    ) {
        cartService.removeCartItem(userName, productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Removed product Id: "+productId+" for userName: "+userName);
    }
}

