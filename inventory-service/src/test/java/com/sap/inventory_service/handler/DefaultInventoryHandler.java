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
        Batch batch1 = Batch.builder().id(1L).quantity(5).expiryDate(LocalDate.now().plusDays(10)).build();
        Batch batch2 = Batch.builder().id(2L).quantity(10).expiryDate(LocalDate.now().plusDays(20)).build();
        List<Batch> batches = Arrays.asList(batch1, batch2);

        when(batchRepository.findByProductIdOrderByExpiryDateAsc(1L)).thenReturn(batches);

        UpdateInventoryRequest request = new UpdateInventoryRequest();
        request.setProductId(1L);
        request.setQuantity(12); // total quantity less than sum of batches

        handler.handle(request);

        // Capture saved batches
        ArgumentCaptor<Batch> captor = ArgumentCaptor.forClass(Batch.class);
        verify(batchRepository, times(2)).save(captor.capture());

        List<Batch> savedBatches = captor.getAllValues();
        assertEquals(0, savedBatches.get(0).getQuantity()); // batch1 fully used
        assertEquals(3, savedBatches.get(1).getQuantity()); // batch2 partially used
    }

    @Test
    void handle_shouldThrowException_whenNotEnoughInventory() {
        Batch batch1 = Batch.builder().id(1L).quantity(5).expiryDate(LocalDate.now().plusDays(10)).build();
        Batch batch2 = Batch.builder().id(2L).quantity(5).expiryDate(LocalDate.now().plusDays(20)).build();
        List<Batch> batches = Arrays.asList(batch1, batch2);

        when(batchRepository.findByProductIdOrderByExpiryDateAsc(1L)).thenReturn(batches);

        UpdateInventoryRequest request = new UpdateInventoryRequest();
        request.setProductId(1L);
        request.setQuantity(15); // more than total available (10)

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            handler.handle(request);
        });

        assertTrue(exception.getMessage().contains("Not enough inventory for product 1"));
        verify(batchRepository, times(2)).save(any(Batch.class)); // partial updates happened before exception
    }
}
