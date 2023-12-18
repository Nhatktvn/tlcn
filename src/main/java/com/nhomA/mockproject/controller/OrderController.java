package com.nhomA.mockproject.controller;

import com.nhomA.mockproject.dto.OrderPaymentVnPayDTO;
import com.nhomA.mockproject.dto.OrderRequestDTO;
import com.nhomA.mockproject.exception.CartLineItemNotFoundException;
import com.nhomA.mockproject.service.OrderService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//    @GetMapping("/vnpay/callback")
//    public ResponseEntity<String> handleVnPayCallback(@RequestParam("infoName") String name) {
//
//    }

    @GetMapping("/user/orders")
    public ResponseEntity<?> getOrderByUser(Authentication authentication){
        try {
            String username = authentication.getName();
            return new ResponseEntity<>(orderService.getOrderByUser(username),HttpStatus.OK);
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (ExpiredJwtException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (AccessDeniedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/order/payment-vnPay")
    public ResponseEntity<?> orderPaymentVnPay (Authentication authentication, @RequestBody OrderPaymentVnPayDTO paymentVnPayDTO){
        String username = authentication.getName();
        try{
            return new ResponseEntity<> (orderService.orderPaymentVnPay(username,paymentVnPayDTO), HttpStatus.CREATED);
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
        catch (CartLineItemNotFoundException ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/order")
    public ResponseEntity<?> order (Authentication authentication, @RequestBody OrderRequestDTO orderRequestDTO){
        String username = authentication.getName();
        try{
            return new ResponseEntity<> (orderService.order(username,orderRequestDTO), HttpStatus.CREATED);
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
        catch (CartLineItemNotFoundException ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/admin/order/update-status")
    public ResponseEntity<?> setStatusOrder(@RequestParam("orderId") Long orderId, @RequestParam("statusOrderId") Long statusOrderId){
        try{
            return new ResponseEntity<> (orderService.setStatusOrder(orderId,statusOrderId), HttpStatus.OK);
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

    @GetMapping("/admin/orders")
    public ResponseEntity<?> getAllOrder(@RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
                                           @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                           @RequestParam(value = "sortBy",defaultValue = "id")String sortBy,
                                           @RequestParam(value = "sortDir",defaultValue = "asc")String sorDir){
        try {
            return new ResponseEntity<>(orderService.getAllOrder(pageNo,pageSize,sortBy,sorDir),HttpStatus.OK);
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (ExpiredJwtException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (AccessDeniedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/cancel-order")
    public ResponseEntity<?> cancelOrder(Authentication authentication,@RequestParam("id") Long id){
        try {
            String username = authentication.getName();
            return new ResponseEntity<>(orderService.cancelOrder(id, username),HttpStatus.OK);
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (ExpiredJwtException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (AccessDeniedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
