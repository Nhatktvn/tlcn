package com.nhomA.mockproject.mapper.impl;

import com.nhomA.mockproject.dto.CartLineItemResponseDTO;
import com.nhomA.mockproject.dto.OrderResponseDTO;
import com.nhomA.mockproject.dto.ProductResponseDTO;
import com.nhomA.mockproject.entity.CartLineItem;
import com.nhomA.mockproject.entity.Order;
import com.nhomA.mockproject.entity.Product;
import com.nhomA.mockproject.mapper.CartLineItemMapper;
import com.nhomA.mockproject.mapper.OrderMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class OrderMapperImpl implements OrderMapper {
    private final CartLineItemMapper cartLineItemMapper;

    public OrderMapperImpl(CartLineItemMapper cartLineItemMapper) {
        this.cartLineItemMapper = cartLineItemMapper;
    }

    @Override
    public OrderResponseDTO toResponseDTO(Order order) {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setAddress(order.getAddress());
        responseDTO.setName(order.getName());
        responseDTO.setPhone(order.getPhoneNumber());
        responseDTO.setTotalPrice(order.getTotalPrice());
        responseDTO.setDeliveryTime(order.getDeliveryTime());
        responseDTO.setStatusOrder(order.getStatusOrder().getName());
        List<CartLineItemResponseDTO> cartLineItemResponseDTOs = new ArrayList<>();
        for(CartLineItem c: order.getCartLineItems()){
            cartLineItemResponseDTOs.add(cartLineItemMapper.toResponseDTO(c));
        }
        responseDTO.setCartLineItemResponseDTOs(cartLineItemResponseDTOs);
        return responseDTO;
    }

    @Override
    public List<OrderResponseDTO> toResponseDTOs(List<Order> orders) {
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        for(Order o: orders){
            orderResponseDTOS.add(this.toResponseDTO(o));
        }
        return orderResponseDTOS;
    }
}
