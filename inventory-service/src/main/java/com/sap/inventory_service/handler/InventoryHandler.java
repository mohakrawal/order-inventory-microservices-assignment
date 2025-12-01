package com.sap.inventory_service.handler;

import com.sap.inventory_service.dto.UpdateInventoryRequest;

public interface InventoryHandler {
    void handle(UpdateInventoryRequest request);
}
