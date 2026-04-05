package com.example.product.management.controller;

import com.example.product.management.entity.Orders;
import com.example.product.management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestParam String userName) {
        Orders order = orderService.placeOrder(userName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Congratulations " + order.getUser().getUserName() + ", your order has been placed successfully.");    }
}

