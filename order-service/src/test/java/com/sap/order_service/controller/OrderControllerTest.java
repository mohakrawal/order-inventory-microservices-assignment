package com.sap.order_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.order_service.dto.OrderRequest;
import com.sap.order_service.entity.Order;
import com.sap.order_service.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private OrderService orderService;


    @Test
    void placeOrder_shouldReturnOrder_whenOrderPlacedSuccessfully() throws Exception {

        OrderRequest req = new OrderRequest();
        req.setProductId(1L);
        req.setQuantity(2);

        Order order = Order.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .status("PLACED")
                .build();

        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(order);

        mockMvc.perform(post("/order-service/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.status").value("PLACED"));
    }

    @Test
    void placeOrder_shouldReturnOrderWithFailedStatus_whenServiceFails() throws Exception {
        OrderRequest req = new OrderRequest();
        req.setProductId(2L);
        req.setQuantity(5);

        Order order = Order.builder()
                .id(2L)
                .productId(2L)
                .quantity(5)
                .status("FAILED")
                .build();

        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(order);

        mockMvc.perform(post("/order-service/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.productId").value(2))
                .andExpect(jsonPath("$.quantity").value(5))
                .andExpect(jsonPath("$.status").value("FAILED"));
    }
}
