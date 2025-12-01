package com.sap.inventory_service.handler;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InventoryHandlerFactory {
    private final Map<String, InventoryHandler> handlers;

    public InventoryHandlerFactory(Map<String, InventoryHandler> handlers) {
        this.handlers = handlers;
    }

    public InventoryHandler getHandler(String key) {
        if (key == null || !handlers.containsKey(key)) {
            return handlers.get("defaultHandler");
        }
        return handlers.get(key);
    }
}
