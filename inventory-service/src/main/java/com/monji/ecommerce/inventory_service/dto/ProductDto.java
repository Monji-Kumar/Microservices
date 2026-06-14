package com.monji.ecommerce.inventory_service.dto;

import lombok.Data;

@Data
public class ProductDto {

    private Long id;
    private String productName;
    private Double price;
    private Integer stock;

}
