package com.monji.ecommerce.order_service.service;

import com.monji.ecommerce.order_service.dto.OrderRequestDto;
import com.monji.ecommerce.order_service.entity.Order;
import com.monji.ecommerce.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderRequestDto.class)).toList();
    }

    public OrderRequestDto getOrderById(Long id) {
        log.info("Getting order by Id : {}", id);
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No Order found with the given ID"));
        return modelMapper.map(order, OrderRequestDto.class);
    }
}
