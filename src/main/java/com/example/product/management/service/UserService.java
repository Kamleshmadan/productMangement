package com.example.product.management.service;

import com.example.product.management.entity.Users;
import com.example.product.management.exception.UserNotFoundException;
import com.example.product.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Users getUserByUsername(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() ->
                new UserNotFoundException("user not found for userName:" + userName));
    }

    public Users addUser(Users user) {
        return userRepository.save(user);
    }

    public boolean userExists(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }

    public Users upsertUser(Users user) {
        return userRepository.findByUserName(user.getUserName())
                .map(existing -> {
                    existing.setPassword(user.getPassword());
                    existing.setRole(user.getRole());
                    return userRepository.save(existing);
                })
                .orElseGet(() -> userRepository.save(user));
    }
}
