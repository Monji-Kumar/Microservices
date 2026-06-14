package com.monji.ecommerce.order_service.dto;

import lombok.Data;

@Data
public class OrderRequestItemDto {
    private Long orderId;
    private long id;
    private Integer quantity;
}
