package com.example.product.management.repository;

import com.example.product.management.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CartItemRepository extends JpaRepository<CartItems,Integer> {
    Optional<CartItems> findByCartIdAndProductId(int id, int productId);

    List<CartItems> findByCartId(int cartId);
}
