package com.sap.inventory_service.handler;

import com.sap.inventory_service.dto.UpdateInventoryRequest;
import com.sap.inventory_service.entity.Batch;
import com.sap.inventory_service.repository.BatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultInventoryHandlerTest {

    @Mock
    private BatchRepository batchRepository;

    @InjectMocks
    private DefaultInventoryHandler handler;

    @Test
    void handle_shouldDeductInventorySuccessfully() {
        Batch batch1 = new Batch();
        batch1.setId(1l);
        batch1.setExpiryDate(LocalDate.now().plusDays(10));
        batch1.setQuantity(5);
        List<Batch> batches = Arrays.asList(batch1);

        when(batchRepository.findByProductIdOrderByExpiryDateAsc(1L)).thenReturn(batches);

        UpdateInventoryRequest request = new UpdateInventoryRequest();
        request.setProductId(1L);
        request.setQuantity(12);

        handler.handle(request);

        // Capture saved batches
        ArgumentCaptor<Batch> captor = ArgumentCaptor.forClass(Batch.class);
        verify(batchRepository, times(1)).save(captor.capture());

        List<Batch> savedBatches = captor.getAllValues();
        assertEquals(0, savedBatches.get(0).getQuantity());
        assertEquals(0, savedBatches.get(0).getQuantity());
    }

    @Test
    void handle_shouldThrowException_whenNotEnoughInventory() {
        Batch batch1 = new Batch();
        batch1.setId(1l);
        batch1.setExpiryDate(LocalDate.now().plusDays(10));
        batch1.setQuantity(5);
        List<Batch> batches = Arrays.asList(batch1);

        when(batchRepository.findByProductIdOrderByExpiryDateAsc(1L)).thenReturn(batches);

        UpdateInventoryRequest request = new UpdateInventoryRequest();
        request.setProductId(1L);
        request.setQuantity(15); // more than total available (10)

        handler.handle(request);

        verify(batchRepository, times(1)).save(any(Batch.class));
    }
}
