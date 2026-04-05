package com.example.product.management.controller;

import com.example.product.management.entity.Users;
import com.example.product.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addUser")
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        boolean exists = userService.userExists(user.getUserName());
        Users saved = userService.upsertUser(user);
        return ResponseEntity.status(exists ? HttpStatus.OK : HttpStatus.CREATED).body(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateUser")
    public ResponseEntity<Users> updateUser(@RequestBody Users user) {
        Users saved = userService.upsertUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(saved);
    }
}
