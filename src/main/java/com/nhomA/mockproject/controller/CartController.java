package com.nhomA.mockproject.controller;

import com.nhomA.mockproject.entity.CartLineItem;
import com.nhomA.mockproject.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<?> addCart(Authentication authentication, @RequestParam("idProduct") Long idProduct, @RequestParam("quantity") int quantity){
        String username = authentication.getName();
        try{
            return new ResponseEntity<> (cartService.addProductToCart(idProduct,quantity,username), HttpStatus.CREATED);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
