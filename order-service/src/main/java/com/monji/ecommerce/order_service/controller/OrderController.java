package com.monji.ecommerce.order_service.controller;

import com.monji.ecommerce.order_service.dto.OrderRequestDto;
import com.monji.ecommerce.order_service.service.OrderService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(path = "hello-order")
    public ResponseEntity<?> helloOrder() {
        return  ResponseEntity.ok("Hello From Order-Service");
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<OrderRequestDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping(path = "/get-order-by-id")
    public ResponseEntity<?> getOrderById(@RequestParam(name = "id") Long id) {
        OrderRequestDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping(path = "/create-order")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderRequestDto orderDto = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderDto);
    }
}
