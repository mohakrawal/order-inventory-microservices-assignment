package com.sap.inventory_service.dto;

import lombok.*;

@Data
public class UpdateInventoryRequest {
    private Long productId;
    private Integer quantity;
}

