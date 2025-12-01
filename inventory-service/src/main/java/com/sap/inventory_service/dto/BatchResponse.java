package com.sap.inventory_service.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchResponse {
    private Long id;
    private Integer quantity;
    private LocalDate expiryDate;
}

