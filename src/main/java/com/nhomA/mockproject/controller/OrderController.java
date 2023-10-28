package com.nhomA.mockproject.controller;

import com.nhomA.mockproject.dto.OrderRequestDTO;
import com.nhomA.mockproject.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/order/{cartId}")
    public ResponseEntity<?> order (Authentication authentication, @RequestBody OrderRequestDTO orderRequestDTO){
        String username = authentication.getName();
        try{
            return new ResponseEntity<> (orderService.order(username,orderRequestDTO), HttpStatus.CREATED);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/admin/order/update-status")
    public ResponseEntity<?> setStatusOrder(@RequestParam("orderId") Long orderId, @RequestParam("statusOrderId") Long statusOrderId){
        try{
            return new ResponseEntity<> (orderService.setStatusOrder(orderId,statusOrderId), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/admin/order/get-all")
    public ResponseEntity<?> getAllOrder(@RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
                                           @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                           @RequestParam(value = "sortBy",defaultValue = "id")String sortBy,
                                           @RequestParam(value = "sortDir",defaultValue = "asc")String sorDir){
        try {
            return new ResponseEntity<>(orderService.getAllOrder(pageNo,pageSize,sortBy,sorDir),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
