package com.sap.order_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInventoryRequest {
    private Long productId;
    private Integer quantity;
}
