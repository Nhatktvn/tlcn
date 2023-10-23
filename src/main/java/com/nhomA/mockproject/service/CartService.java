package com.nhomA.mockproject.service;

import com.nhomA.mockproject.dto.CartLineItemResponseDTO;

public interface CartService {
    CartLineItemResponseDTO addProductToCart(Long idProduct, int quantity, String username);
}
