package com.nhomA.mockproject.controller;

import com.nhomA.mockproject.dto.AddCartDTO;
import com.nhomA.mockproject.entity.CartLineItem;
import com.nhomA.mockproject.service.CartService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()

public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/cart/add-cart")
    public ResponseEntity<?> addCart(Authentication authentication, @RequestBody AddCartDTO addCartDTO){
        String username = authentication.getName();
        try{
            return new ResponseEntity<> (cartService.addProductToCart(addCartDTO.getIdProduct(), addCartDTO.getQuantity(),username), HttpStatus.CREATED);
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (ExpiredJwtException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (AccessDeniedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/cart/delete-items/{idProduct}")
    public ResponseEntity<?> deleteProduct (Authentication authentication, @PathVariable("IdProduct") Long idProduct){
        String username = authentication.getName();
        try{
            return new ResponseEntity<> (cartService.removeProductCart(idProduct,username), HttpStatus.CREATED);
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (ExpiredJwtException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (AccessDeniedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("cart/set-quantity/{idProduct}")
    public ResponseEntity<?> deleteItem (Authentication authentication, @PathVariable("IdProduct") Long idProduct,@RequestParam("quantity") int quantity){
        String username = authentication.getName();
        try{
            return new ResponseEntity<> (cartService.updateQuantityProduct(username,idProduct,quantity), HttpStatus.OK);
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (ExpiredJwtException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (AccessDeniedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/user/get-cart")
    public ResponseEntity<?> getCartLineItems (Authentication authentication ){
        String username = authentication.getName();
        try{
            return new ResponseEntity<> (cartService.getAllCartLineItemUsername(username), HttpStatus.OK);
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (ExpiredJwtException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (AccessDeniedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
