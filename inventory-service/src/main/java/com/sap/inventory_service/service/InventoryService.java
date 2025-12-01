package com.sap.inventory_service.service;

import com.sap.inventory_service.dto.BatchResponse;
import com.sap.inventory_service.dto.UpdateInventoryRequest;
import com.sap.inventory_service.entity.Batch;
import com.sap.inventory_service.handler.InventoryHandler;
import com.sap.inventory_service.handler.InventoryHandlerFactory;
import com.sap.inventory_service.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final BatchRepository batchRepository;
    private final InventoryHandlerFactory factory;

    public List<BatchResponse> getBatchesForProduct(Long productId) {
        List<Batch> batches = batchRepository.findByProductIdOrderByExpiryDateAsc(productId);
        return batches.stream()
                .map(b -> BatchResponse.builder()
                        .id(b.getId())
                        .quantity(b.getQuantity())
                        .expiryDate(b.getExpiryDate())
                        .build())
                .collect(Collectors.toList());
    }

    public void updateInventory(UpdateInventoryRequest request, String handlerKey) {
        InventoryHandler handler = factory.getHandler(handlerKey);
        handler.handle(request);
    }
}
