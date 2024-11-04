package microservices.order_service.service;

import java.sql.Timestamp;

import org.springframework.http.ResponseEntity;

import microservices.order_service.model.Response;

public interface OrderService {
    public ResponseEntity<Response> createOrder(String productId, int quantity, Double totalPrice, Timestamp orderDate, String customerName, String customerAddress, String customerEmail);
    public ResponseEntity<Response> editOrder(Long id, String productId, int quantity, Double totalPrice, Timestamp orderDate, String status, String customerName, String customerAddress, String customerEmail);
    public ResponseEntity<Response> deleteOrder(Long id);
    public ResponseEntity<Response> findOrderById(Long id);   
    public ResponseEntity<Response> getAllOrders();
}
