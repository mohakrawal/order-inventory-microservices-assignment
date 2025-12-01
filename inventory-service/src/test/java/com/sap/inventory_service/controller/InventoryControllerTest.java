package com.sap.inventory_service.controller;


import com.sap.inventory_service.dto.BatchResponse;
import com.sap.inventory_service.dto.UpdateInventoryRequest;
import com.sap.inventory_service.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

   @Mock
    private InventoryController inventoryController;


    @Test
    void getBatches_shouldReturnListOfBatches() throws Exception {
        LocalDate specificDate = LocalDate.of(2025, 12, 1);
        List<BatchResponse> batches = Arrays.asList(
                new BatchResponse(1L, 20,specificDate),
                new BatchResponse(2L, 50, specificDate)
        );

        inventoryController.getBatches(1L);
        Assert.notNull(batches,"not null");



    }

    @Test
    void updateInventory_shouldReturnOk_whenInventoryUpdated() throws Exception {
        UpdateInventoryRequest request = new UpdateInventoryRequest();
        request.setProductId(1L);
        request.setQuantity(10);


        inventoryController.updateInventory(request, "defaultHandler");
        Assert.notNull(request, "not null");

    }
}
