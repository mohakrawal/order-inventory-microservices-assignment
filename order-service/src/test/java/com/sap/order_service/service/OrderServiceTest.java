package com.sap.order_service.service;

import com.sap.order_service.dto.OrderRequest;
import com.sap.order_service.dto.UpdateInventoryRequest;
import com.sap.order_service.entity.Order;
import com.sap.order_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void placeOrder_successfulInventoryUpdate_shouldReturnPlacedOrder() {
        OrderRequest req = new OrderRequest();
        req.setProductId(1L);
        req.setQuantity(5);

        when(restTemplate.postForEntity(anyString(), any(UpdateInventoryRequest.class), eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Order result = orderService.placeOrder(req);

        assertEquals("PLACED", result.getStatus());
        assertEquals(1L, result.getProductId());
        assertEquals(5, result.getQuantity());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void placeOrder_inventoryUpdateFails_shouldReturnFailedOrder() {
        // Arrange
        OrderRequest req = new OrderRequest();
        req.setProductId(1L);
        req.setQuantity(5);

        when(restTemplate.postForEntity(anyString(), any(UpdateInventoryRequest.class), eq(Void.class)))
                .thenThrow(new RuntimeException("Inventory service down"));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Order result = orderService.placeOrder(req);

        assertEquals("FAILED", result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
