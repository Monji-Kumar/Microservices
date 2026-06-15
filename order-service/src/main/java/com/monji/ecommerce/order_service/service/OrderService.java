package com.monji.ecommerce.order_service.service;

import com.monji.ecommerce.order_service.client.InventoryFiegnClient;
import com.monji.ecommerce.order_service.dto.OrderRequestDto;
import com.monji.ecommerce.order_service.entity.Order;
import com.monji.ecommerce.order_service.entity.OrderItem;
import com.monji.ecommerce.order_service.enums.OrderStatus;
import com.monji.ecommerce.order_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryFiegnClient inventoryFiegnClient;

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

//    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
//    @RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "createOrderFallback")
    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "createOrderFallback")
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        log.info("Creating order : {}", orderRequestDto);
        Double totalPrice = inventoryFiegnClient.reduceProductStock(orderRequestDto);

        // Map order (without items)
        Order order = new Order();
        order.setTotalPrice(totalPrice);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        // Map items manually
        List<OrderItem> orderItems = orderRequestDto.getItems().stream()
                .map(itemDto -> {
                    OrderItem item = new OrderItem();
                    item.setProductId(itemDto.getProductId());
                    item.setQuantity(itemDto.getQuantity());
                    item.setOrder(order); // set back-reference
                    return item;
                })
                .toList();

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderRequestDto.class);
    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto, Throwable throwable) {
        log.error("Creating order fallback due to : {}", throwable.getMessage());
        return new OrderRequestDto();
    }
}
