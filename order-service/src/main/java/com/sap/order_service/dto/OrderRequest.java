package com.sap.order_service.dto;

import lombok.*;

@Data
public class OrderRequest {
    private Long productId;
    private Integer quantity;
}
