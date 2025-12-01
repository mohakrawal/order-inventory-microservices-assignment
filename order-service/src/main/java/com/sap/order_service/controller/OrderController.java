package com.sap.order_service.controller;

import com.sap.order_service.dto.OrderRequest;
import com.sap.order_service.entity.Order;
import com.sap.order_service.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-service")

public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Accept and process orders
     * @param req
     * @return
     */
    @PostMapping("/order")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest req) {
        return ResponseEntity.ok(orderService.placeOrder(req));
    }
}

