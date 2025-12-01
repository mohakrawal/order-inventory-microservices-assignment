package com.sap.inventory_service.controller;

import com.sap.inventory_service.dto.BatchResponse;
import com.sap.inventory_service.dto.UpdateInventoryRequest;
import com.sap.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-service")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService service;

    @GetMapping("/{productId}")
    public ResponseEntity<List<BatchResponse>> getBatches(@PathVariable Long productId) {
        return ResponseEntity.ok(service.getBatchesForProduct(productId));
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateInventory(@RequestBody UpdateInventoryRequest request,
                                                @RequestParam(required = false) String handler) {
        service.updateInventory(request, handler);
        return ResponseEntity.ok().build();
    }
}
