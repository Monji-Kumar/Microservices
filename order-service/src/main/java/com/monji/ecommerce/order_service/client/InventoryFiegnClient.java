package com.monji.ecommerce.order_service.client;

import com.monji.ecommerce.order_service.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", path = "/api/v1/products")
public interface InventoryFiegnClient {

    @PutMapping(path = "/reduce-stock")
    Double reduceProductStock(@RequestBody OrderRequestDto orderRequestDto);
}
