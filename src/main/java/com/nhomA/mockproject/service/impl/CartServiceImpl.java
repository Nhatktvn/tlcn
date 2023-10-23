package com.nhomA.mockproject.service.impl;

import com.nhomA.mockproject.dto.CartLineItemResponseDTO;
import com.nhomA.mockproject.entity.Cart;
import com.nhomA.mockproject.entity.CartLineItem;
import com.nhomA.mockproject.entity.Product;
import com.nhomA.mockproject.entity.User;
import com.nhomA.mockproject.exception.CartLineItemNotFoundException;
import com.nhomA.mockproject.exception.CartNotFoundException;
import com.nhomA.mockproject.exception.VariantProductNotFoundException;
import com.nhomA.mockproject.mapper.CartLineItemMapper;
import com.nhomA.mockproject.repository.CartLineItemRepository;
import com.nhomA.mockproject.repository.CartRepository;
import com.nhomA.mockproject.repository.ProductRepository;
import com.nhomA.mockproject.repository.UserRepository;
import com.nhomA.mockproject.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartLineItemRepository cartLineItemRepository;
    private final CartLineItemMapper cartLineItemMapper;
    public CartServiceImpl(UserRepository userRepository, CartRepository cartRepository, ProductRepository productRepository, CartLineItemRepository cartLineItemRepository, CartLineItemMapper cartLineItemMapper) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartLineItemRepository = cartLineItemRepository;
        this.cartLineItemMapper = cartLineItemMapper;
    }

    @Transactional
    @Override
    public CartLineItemResponseDTO addProductToCart(Long idProduct, int quantity, String username) {
        Optional<User> emptyUser =  userRepository.findByUsername(username);
        User user = emptyUser.get();
        Cart cart = user.getCart();
        Optional<Product> emptyProduct = productRepository.findById(idProduct);
        if(emptyProduct.isEmpty()){
            throw new VariantProductNotFoundException("Variant product is not found!");
        }
        Optional<CartLineItem> emptyCartLineItem = cartLineItemRepository.findByCartIdAndProductIdAndIsDeleted(cart.getId(),idProduct,false);
        if (emptyCartLineItem.isEmpty()){
            CartLineItem cartLineItem = new CartLineItem();
            cartLineItem.setProduct(emptyProduct.get());
            cartLineItem.setCart(cart);
            cartLineItem.setQuantity(quantity);
            cartLineItem.setTotalPrice(quantity * emptyProduct.get().getPrice() - quantity * emptyProduct.get().getPrice() * emptyProduct.get().getDiscount());
            cartLineItem.setAddedDate(ZonedDateTime.now());
            cartLineItem.setCart(cart);
            CartLineItem saveCartLineItem = cartLineItemRepository.save(cartLineItem);
            System.out.println(saveCartLineItem);
            return cartLineItemMapper.toResponseDTO(saveCartLineItem);
        }
        //create new cart_line_item
        CartLineItem cartLineItem = emptyCartLineItem.get();
        int quantityNew = cartLineItem.getQuantity() + quantity;
        cartLineItem.setQuantity(quantityNew);
        double priceNew = quantityNew * emptyProduct.get().getPrice() - quantityNew * emptyProduct.get().getPrice() *  emptyProduct.get().getDiscount();
        cartLineItem.setTotalPrice(priceNew);
        CartLineItem saveCartLineItem = cartLineItemRepository.save(cartLineItem);
        return cartLineItemMapper.toResponseDTO(saveCartLineItem);
    }
    @Transactional
    @Override
    public boolean removeProductCart(Long idProduct, String username) {
        Optional<User> emptyUser =  userRepository.findByUsername(username);
        User user = emptyUser.get();
        Cart cart = user.getCart();
        Optional<CartLineItem> emptyCartLineItem = cartLineItemRepository.findByCartIdAndProductIdAndIsDeleted(cart.getId(),idProduct,false);
        if(emptyCartLineItem.isEmpty()){
            throw new CartLineItemNotFoundException("Cart line item not found");
        }
        cartLineItemRepository.delete(emptyCartLineItem.get());
        return true;
    }

    @Override
    public CartLineItemResponseDTO updateQuantityProduct(String username, Long idProduct, int quantity) {
        Optional<User> emptyUser =  userRepository.findByUsername(username);
        User user = emptyUser.get();
        Cart cart = user.getCart();
        Optional<CartLineItem> emptyCartLineItem = cartLineItemRepository.findByCartIdAndProductIdAndIsDeleted(cart.getId(),idProduct,false);
        if(emptyCartLineItem.isEmpty()){
            throw new CartLineItemNotFoundException("Cart line item not found");
        }
        CartLineItem cartLineItem = emptyCartLineItem.get();
        cartLineItem.setQuantity(quantity);
        CartLineItem saveCartLineItem = cartLineItemRepository.save(cartLineItem);
        return cartLineItemMapper.toResponseDTO(saveCartLineItem);
    }
}
