package com.monji.ecommerce.inventory_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "order-service", path = "/api/v1/orders")
public interface OrderFeignClient {

    @GetMapping(path = "/hello-order")
    String helloOrder();
}
