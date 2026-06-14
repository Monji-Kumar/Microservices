package com.monji.ecommerce.inventory_service.controller;

import com.monji.ecommerce.inventory_service.dto.ProductDto;
import com.monji.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

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
}
