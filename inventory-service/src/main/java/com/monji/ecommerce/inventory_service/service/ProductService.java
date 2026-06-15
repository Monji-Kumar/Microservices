package com.monji.ecommerce.inventory_service.service;

import com.monji.ecommerce.inventory_service.dto.OrderRequestDto;
import com.monji.ecommerce.inventory_service.dto.OrderRequestItemDto;
import com.monji.ecommerce.inventory_service.dto.ProductDto;
import com.monji.ecommerce.inventory_service.entity.Product;
import com.monji.ecommerce.inventory_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllInventory() {
        log.info("Getting all inventory");
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
    }

    public ProductDto getInventoryById(Long id) {
        log.info("Getting inventory with id {}", id);
        Product product = productRepository.findById(id).get();
        return modelMapper.map(product, ProductDto.class);
    }

    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        log.info("Reducing stocks");
        Double totalPrice = 0.0;
        for(OrderRequestItemDto item : orderRequestDto.getItems()) {
            Long productId = item.getProductId();
            Integer quantity = item.getQuantity();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product with id " + productId + " not found"));

            if(product.getStock() < quantity) {
                throw new RuntimeException("Product with id " + productId + " not enough stock");
            }

            product.setStock(product.getStock() - quantity);
            totalPrice += product.getPrice() * quantity;
            productRepository.save(product);
        }

        return totalPrice;
    }
}
