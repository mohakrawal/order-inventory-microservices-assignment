package com.sap.order_service.controller;

import com.sap.order_service.dto.OrderRequest;
import com.sap.order_service.entity.Order;
import com.sap.order_service.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void placeOrder_shouldReturnOrder_whenOrderPlacedSuccessfully() {

        OrderRequest request = new OrderRequest();
        request.setProductId(1L);
        request.setQuantity(2);

        Order order = Order.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .status("PLACED")
                .build();

        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(order);


        ResponseEntity<Order> response = orderController.placeOrder(request);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals(1L, response.getBody().getProductId());
        assertEquals(2, response.getBody().getQuantity());
        assertEquals("PLACED", response.getBody().getStatus());
    }

    @Test
    void placeOrder_shouldReturnOrderWithFailedStatus_whenServiceFails() {

        OrderRequest request = new OrderRequest();
        request.setProductId(2L);
        request.setQuantity(5);

        Order order = Order.builder()
                .id(2L)
                .productId(2L)
                .quantity(5)
                .status("FAILED")
                .build();

        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(order);


        ResponseEntity<Order> response = orderController.placeOrder(request);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2L, response.getBody().getId());
        assertEquals(2L, response.getBody().getProductId());
        assertEquals(5, response.getBody().getQuantity());
        assertEquals("FAILED", response.getBody().getStatus());
    }
}
