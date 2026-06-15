package com.monji.ecommerce.inventory_service.controller;

import com.monji.ecommerce.inventory_service.clients.OrderFeignClient;
import com.monji.ecommerce.inventory_service.dto.OrderRequestDto;
import com.monji.ecommerce.inventory_service.dto.ProductDto;
import com.monji.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    private final OrderFeignClient orderFeignClient;

    @GetMapping(path = "fetch-orders")
    public ResponseEntity<?> fetchOrdersFromOrderService() {
//        ServiceInstance orderServiceInstance = discoveryClient.getInstances("order-service").get(0);
//        String response = restClient.get().uri(orderServiceInstance.getUri() + "/api/v1/orders/hello-order")
//                .retrieve().body(String.class);

        String response = String.valueOf(orderFeignClient.helloOrder());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<ProductDto> products = productService.getAllInventory();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/get-product-by-id")
    public ResponseEntity<?> getProductById(@RequestParam(name = "id") Long id) {
        ProductDto product = productService.getInventoryById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping(path = "/reduce-stock")
    public ResponseEntity<?> reduceProductStockById(@RequestBody OrderRequestDto orderRequestDto) {
        Double totalPrice = productService.reduceStocks(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }
}
