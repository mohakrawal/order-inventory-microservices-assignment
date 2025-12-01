package com.sap.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long productId;
    private Integer quantity;
    private String status;
}
