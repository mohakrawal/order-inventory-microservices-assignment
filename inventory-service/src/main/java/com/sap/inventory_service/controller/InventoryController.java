package com.sap.inventory_service.controller;

import com.sap.inventory_service.dto.BatchResponse;
import com.sap.inventory_service.dto.UpdateInventoryRequest;
import com.sap.inventory_service.entity.Batch;
import com.sap.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService service;

    /**
     * Get product based on ID
     *
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    public ResponseEntity<List<BatchResponse>> getBatches(@PathVariable Long productId) {
        try {
            final List<BatchResponse> response = service.getBatchesForProduct(productId);
            if (response.isEmpty()) {
                throw new RuntimeException("Product not found");
            }

            return ResponseEntity.ok(service.getBatchesForProduct(productId));
        } catch (RuntimeException ex) {
            System.out.println("Product not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * check stock in inventory and update quantity
     *
     * @param request
     * @param handler
     * @return
     */
    @PostMapping("/update")
    public String updateInventory(@RequestBody UpdateInventoryRequest request,
                                                @RequestParam(required = false) String handler) {
        String msg= service.updateInventory(request, handler);
        return msg;
    }
}
