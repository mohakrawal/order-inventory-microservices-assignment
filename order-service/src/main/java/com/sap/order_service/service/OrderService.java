package com.sap.order_service.service;

import com.sap.order_service.dto.OrderRequest;
import com.sap.order_service.dto.UpdateInventoryRequest;
import com.sap.order_service.entity.Order;
import com.sap.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    private final String inventoryBaseUrl = "http://localhost:8081";

    public Order placeOrder(OrderRequest req) {
        UpdateInventoryRequest update = new UpdateInventoryRequest(req.getProductId(), req.getQuantity());
        try {
            ResponseEntity<Void> resp = restTemplate.postForEntity(inventoryBaseUrl + "/inventory-service/update", update, Void.class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                Order order = Order.builder()
                        .productId(req.getProductId())
                        .quantity(req.getQuantity())
                        .status("PLACED")
                        .build();
                return orderRepository.save(order);
            } else {
                Order order = Order.builder()
                        .productId(req.getProductId())
                        .quantity(req.getQuantity())
                        .status("FAILED")
                        .build();
                return orderRepository.save(order);
            }
        } catch (RuntimeException ex) {
            Order order = Order.builder()
                    .productId(req.getProductId())
                    .quantity(req.getQuantity())
                    .status("FAILED")
                    .build();
            return orderRepository.save(order);
        }
    }
}
