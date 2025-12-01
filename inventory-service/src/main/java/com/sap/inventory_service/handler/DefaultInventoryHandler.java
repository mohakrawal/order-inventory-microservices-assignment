package com.sap.inventory_service.handler;

import com.sap.inventory_service.dto.UpdateInventoryRequest;
import com.sap.inventory_service.entity.Batch;
import com.sap.inventory_service.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("defaultHandler")
@RequiredArgsConstructor
public class DefaultInventoryHandler implements InventoryHandler {
    private final BatchRepository batchRepository;

    @Override
    @Transactional
    public String handle(UpdateInventoryRequest request) {
        List<Batch> batches = batchRepository.findByProductIdOrderByExpiryDateAsc(request.getProductId());
        int remaining = request.getQuantity();
        for (Batch b : batches) {
            if (remaining <= 0) break;
            int take = Math.min(b.getQuantity(), remaining);
            b.setQuantity(b.getQuantity() - take);
            remaining -= take;
            batchRepository.save(b);
        }
        if (remaining > 0) {
            return "Not enough inventory for product " + request.getProductId();
        }
        return "Update successful";
    }
}
