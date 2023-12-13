package com.nhomA.mockproject.service.impl;

import com.nhomA.mockproject.dto.OrderRequestDTO;
import com.nhomA.mockproject.dto.OrderResponseDTO;
import com.nhomA.mockproject.entity.*;
import com.nhomA.mockproject.exception.AddressNotFoundException;
import com.nhomA.mockproject.exception.CartLineItemNotFoundException;
import com.nhomA.mockproject.exception.OrderNotFoundException;
import com.nhomA.mockproject.exception.StatusOrderNotFoundException;
import com.nhomA.mockproject.mapper.OrderMapper;
import com.nhomA.mockproject.repository.*;
import com.nhomA.mockproject.service.OrderService;
import com.nhomA.mockproject.util.PaginationAndSortingUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final CartLineItemRepository cartLineItemRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final StatusOrderRepository statusOrderRepository;
    public OrderServiceImpl(AddressRepository addressRepository, UserRepository userRepository, CartLineItemRepository cartLineItemRepository, OrderRepository orderRepository, OrderMapper orderMapper, StatusOrderRepository statusOrderRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.cartLineItemRepository = cartLineItemRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.statusOrderRepository = statusOrderRepository;
    }
    @Transactional
    @Override
    public OrderResponseDTO order(String username, OrderRequestDTO orderRequestDTO) {
//        List<CartLineItem> cartLineItems = cartLineItemRepository.findByCartId(cartId);
//        Order order = new Order();
//        order.setAddress(orderRequestDTO.getAddress());
//        order.setName(orderRequestDTO.getName());
//        order.setPhoneNumber(orderRequestDTO.getPhone());
//        order.setDeliveryTime(ZonedDateTime.now());
//        order.setCartLineItems(cartLineItems);
//        double totalPriceOrder = 0;
//        for (CartLineItem c: cartLineItems){
//            totalPriceOrder += c.getTotalPrice();
//        }
//        order.setTotalPrice(totalPriceOrder);
//        orderRepository.save(order);
        Optional<User> emptyUser =  userRepository.findByUsername(username);
        User user = emptyUser.get();
        Cart cart = user.getCart();
        List<CartLineItem> cartLineItems = cartLineItemRepository.findByCartIdAndIsDeleted(cart.getId(), false);
        if(cartLineItems.isEmpty()){
            throw new CartLineItemNotFoundException("Cart line item not found!");
        }
        Optional<Address> existedAddress = addressRepository.findByIdAndUserId(orderRequestDTO.getAddressId(),user.getId());
        if(existedAddress.isEmpty()){
            throw new AddressNotFoundException("Address not found!");
        }
        Address address = existedAddress.get();
        Order order = new Order();
        order.setAddress(address);
        order.setName(orderRequestDTO.getName());
        order.setPhoneNumber(orderRequestDTO.getPhone());
        order.setDeliveryTime(ZonedDateTime.now());
        order.setCartLineItems(cartLineItems);
        double totalPriceOrder = 0;
        for (CartLineItem c: cartLineItems){
            totalPriceOrder += c.getTotalPrice();
        }
        order.setTotalPrice(totalPriceOrder);
        for (CartLineItem c: cartLineItems){
            c.setDeleted(true);
            c.setOrder(order);
        }
        order.setCartLineItems(cartLineItems);
        Optional<StatusOrder> statusOrder = statusOrderRepository.findById(1L);
        order.setStatusOrder(statusOrder.get());
        order.setUser(user);
        user.getOrders().add(order);
//        address.getOrders().add(order);
//        addressRepository.save(address);
        orderRepository.save(order);
        return orderMapper.toResponseDTO(order);
    }

    @Transactional
    @Override
    public List<OrderResponseDTO> getAllOrder(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable= PaginationAndSortingUtils.getPageable(pageNo,pageSize,sortBy,sortDir);
        Page<Order> orders= orderRepository.findAll(pageable);
        List<Order> orderContent = orders.getContent();
        return orderMapper.toResponseDTOs(orderContent);
    }

    @Transactional
    @Override
    public OrderResponseDTO setStatusOrder(Long orderId, Long statusOrderId) {
        Optional<Order> emptyOrder = orderRepository.findById(orderId);
        if(emptyOrder.isEmpty()){
            throw new OrderNotFoundException("Order not found!");
        }
        Optional<StatusOrder> emptyStatusOrder = statusOrderRepository.findById(statusOrderId);
        if(emptyStatusOrder.isEmpty()){
            throw new StatusOrderNotFoundException("Cart line item not found!");
        }
        Order order = emptyOrder.get();
        order.setStatusOrder(emptyStatusOrder.get());
        Order saveOrder = orderRepository.save(order);
        return orderMapper.toResponseDTO(saveOrder);
    }

    @Transactional
    @Override
    public boolean cancelOrder(Long idOrder, String username) {
        Optional<Order> existedOrder = orderRepository.findById(idOrder);
        if(existedOrder.isEmpty()){
            throw new OrderNotFoundException("Order not found!");
        }
        Order order = existedOrder.get();
        Long idStatusCurrent = order.getStatusOrder().getId();
        if(idStatusCurrent == 1){
            Optional<StatusOrder> emptyStatusOrder = statusOrderRepository.findById(6L);
            order.setStatusOrder(emptyStatusOrder.get());
            Order saveOrder = orderRepository.save(order);
            return true;
        }
        return false;
    }
}
