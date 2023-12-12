package com.nhomA.mockproject.service;

import com.nhomA.mockproject.dto.CartLineItemResponseDTO;
import com.nhomA.mockproject.dto.CartLineItemsDTO;

import java.util.List;

public interface CartService {
    CartLineItemResponseDTO addProductToCart(Long idProduct, int quantity, String username);
    boolean removeProductCart(Long idProduct, String username);
    CartLineItemResponseDTO updateQuantityProduct (String username,Long idProduct,int quantity);

    List<CartLineItemResponseDTO> getAllCartLineItemUsername (String username);
}
