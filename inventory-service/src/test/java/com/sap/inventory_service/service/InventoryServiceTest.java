package com.sap.inventory_service.service;

import com.sap.inventory_service.dto.BatchResponse;
import com.sap.inventory_service.dto.UpdateInventoryRequest;
import com.sap.inventory_service.entity.Batch;
import com.sap.inventory_service.handler.InventoryHandler;
import com.sap.inventory_service.handler.InventoryHandlerFactory;
import com.sap.inventory_service.repository.BatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private BatchRepository batchRepository;

    @Mock
    private InventoryHandlerFactory factory;

    @Mock
    private InventoryHandler handler;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void getBatchesForProduct_shouldReturnListOfBatchResponses() {
        LocalDate expiryDate = LocalDate.of(2025, 12, 1);
        List<Batch> batches = Arrays.asList(
                Batch.builder().id(1L).quantity(20).expiryDate(expiryDate).build(),
                Batch.builder().id(2L).quantity(50).expiryDate(expiryDate).build()
        );

        when(batchRepository.findByProductIdOrderByExpiryDateAsc(1L)).thenReturn(batches);

        List<BatchResponse> result = inventoryService.getBatchesForProduct(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(20, result.get(0).getQuantity());
        assertEquals(expiryDate, result.get(0).getExpiryDate());
        assertEquals(50, result.get(1).getQuantity());
    }

    @Test
    void updateInventory_shouldCallHandler() {
        UpdateInventoryRequest request = new UpdateInventoryRequest();
        request.setProductId(1L);
        request.setQuantity(10);

        when(factory.getHandler("defaultHandler")).thenReturn(handler);

        inventoryService.updateInventory(request, "defaultHandler");

        verify(factory).getHandler("defaultHandler");
        verify(handler).handle(request);
    }
}
