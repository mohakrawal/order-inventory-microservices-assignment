package com.sap.order_service.service;

import com.sap.order_service.dto.OrderRequest;
import com.sap.order_service.dto.UpdateInventoryRequest;
import com.sap.order_service.entity.Order;
import com.sap.order_service.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    private static final String inventoryBaseUrl = "http://localhost:8081/inventory-service/update";

    public OrderService(final OrderRepository orderRepository,final RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * update order in inventory and save order
     * @param req
     * @return
     */
    public Order placeOrder(final OrderRequest req) {
        UpdateInventoryRequest update = new UpdateInventoryRequest(req.getProductId(), req.getQuantity());
        try {
            ResponseEntity<Void> resp = restTemplate.postForEntity(inventoryBaseUrl, update, Void.class);
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
        } catch (final RuntimeException ex) {
            Order order = Order.builder()
                    .productId(req.getProductId())
                    .quantity(req.getQuantity())
                    .status("FAILED")
                    .build();
            return orderRepository.save(order);
        }
    }
}
