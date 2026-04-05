package com.example.product.management.service;

import com.example.product.management.entity.Cart;
import com.example.product.management.entity.CartItems;
import com.example.product.management.entity.Products;
import com.example.product.management.entity.Users;
import com.example.product.management.exception.CartItemNotFoundException;
import com.example.product.management.exception.CartNotFoundException;
import com.example.product.management.repository.CartItemRepository;
import com.example.product.management.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    public Cart addToCart(String userName, int productId, int quantity) {
        Users user = userService.getUserByUsername(userName);
        Products product = productService.getProductById(productId);
        if (!product.isEnable()) {
            throw new IllegalStateException("Product is disabled");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> cartRepository.save(new Cart(user)));
        CartItems item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseGet(() -> new CartItems(cart, product, 0));
        item.setQuantity(item.getQuantity() + quantity);
        CartItems newCartItem = cartItemRepository.save(item);
        return cart;
    }

    public CartItems updateCartItem(String userName, int productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }
        Users user = userService.getUserByUsername(userName);
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found for userName: " + userName));
        CartItems item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found for productID: " + productId));
        Products product = productService.getProductById(productId);

        if (!product.isEnable()) {
            throw new IllegalStateException("Product is disabled");
        }
        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    public void removeCartItem(String userName, int productId) {
        Users user = userService.getUserByUsername(userName);
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found for userName: " + userName));
        CartItems item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found for productID: " + productId));
        cartItemRepository.delete(item);
    }


    public Cart getCartByUserId(int userId) {
        return cartRepository.findByUserId(userId).orElseThrow(() ->
                new CartNotFoundException("Cart not found for userId: " + userId));
    }

    public List<CartItems> getCartItemsByCartId(int cartId) {
        List<CartItems> cartItems = cartItemRepository.findByCartId(cartId);
        if (cartItems.isEmpty()) {
            throw new CartItemNotFoundException("Cart item not found for cartId: " + cartId);
        }
        return cartItems;
    }

    public void deleteCartItemsByCartId(int cartId) {
        cartRepository.deleteById(cartId);
    }
}
