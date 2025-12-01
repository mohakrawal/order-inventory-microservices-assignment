package com.sap.inventory_service.entity;

import com.sap.inventory_service.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private Integer quantity;
    private LocalDate expiryDate;
}
