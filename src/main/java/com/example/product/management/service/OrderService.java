package com.example.product.management.service;

import com.example.product.management.entity.*;
import com.example.product.management.exception.InsufficientInventoryException;
import com.example.product.management.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @Transactional
    public Orders placeOrder(String userName) {
        Users user = userService.getUserByUsername(userName);
        Cart cart = cartService.getCartByUserId(user.getId());
        List<CartItems> cartItems = cartService.getCartItemsByCartId(cart.getId());
        double total = 0;

        List<Products> productsToLock = new ArrayList<>();
        for (CartItems ci : cartItems) {
            Products p = productService.getProductById(ci.getProduct().getId());
            productsToLock.add(p);
            if (!p.isEnable()) {
                throw new IllegalStateException("Product is disabled: " + p.getId());
            }
            if (p.getQuantity() < ci.getQuantity()) {
                throw new InsufficientInventoryException(
                        "Insufficient inventory for productId=" + p.getId()
                );
            }
            double lineTotal = p.getPrice() * ci.getQuantity();
            total = total + lineTotal;
        }
        Orders order = new Orders();
        order.setUser(user);
        order.setTotalAmount(total);
        order.setStatus("PLACED");
        List<OrderItems> orderItems = new ArrayList<>();
        for (CartItems ci : cartItems) {
            Products p = productService.getProductById(ci.getProduct().getId());
            p.setQuantity(p.getQuantity() - ci.getQuantity());
            productService.addProduct(p);
            OrderItems oi = new OrderItems();
            oi.setOrder(order);
            oi.setProducts(p);
            oi.setUnitPrice(p.getPrice());
            oi.setQuantity(ci.getQuantity());
            oi.setTotalPrice(p.getPrice() * ci.getQuantity());
            orderItems.add(oi);
        }
        order.setOrderItems(orderItems);
        Orders savedOrder = orderRepository.save(order);
        return savedOrder;
    }
}
