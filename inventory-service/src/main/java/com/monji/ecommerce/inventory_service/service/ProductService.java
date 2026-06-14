package com.monji.ecommerce.inventory_service.service;

import com.monji.ecommerce.inventory_service.dto.ProductDto;
import com.monji.ecommerce.inventory_service.entity.Product;
import com.monji.ecommerce.inventory_service.repository.ProductRepository;
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
}
