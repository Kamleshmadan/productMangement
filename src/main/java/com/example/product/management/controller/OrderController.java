package com.example.product.management.controller;

import com.example.product.management.entity.Orders;
import com.example.product.management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<Orders> placeOrder(@RequestParam String userName) {
        Orders order = orderService.placeOrder(userName);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}

