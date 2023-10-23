package com.nhomA.mockproject.repository;

import com.nhomA.mockproject.entity.CartLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartLineItemRepository extends JpaRepository<CartLineItem, Long> {
    List<CartLineItem> findByCartIdAndIsDeleted (Long cartId, boolean isDeleted);
    Optional<CartLineItem> findByCartIdAndProductIdAndIsDeleted (Long cartId, Long productId, boolean isDeleted);
    List<CartLineItem> findByCartIdAndProductId (Long cartId, Long id);
}
