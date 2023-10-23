package com.nhomA.mockproject.service;

import com.nhomA.mockproject.dto.CartLineItemResponseDTO;

public interface CartService {
    CartLineItemResponseDTO addProductToCart(Long idProduct, int quantity, String username);
    boolean removeProductCart(Long idProduct, String username);
    CartLineItemResponseDTO updateQuantityProduct (String username,Long idProduct,int quantity);
}
