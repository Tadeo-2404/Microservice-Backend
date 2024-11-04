package microservices.order_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservices.order_service.model.Response;
import microservices.order_service.service.Implementation.OrderServiceImpl;
import microservices.order_service.utils.OrderRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/order/v1")
public class OrderController {
    private final OrderServiceImpl orderServiceImpl;

    public OrderController(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @GetMapping("/getOrder") 
    public ResponseEntity<Response> getAllOrders() {
        return orderServiceImpl.getAllOrders();
    }

    @GetMapping("/getOrder/{orderId}")
    public ResponseEntity<Response> findOrderById(@PathVariable Long orderId) {
        return orderServiceImpl.findOrderById(orderId);
    }

    @PostMapping("/create-order")
    public ResponseEntity<Response> createOrder(@RequestBody OrderRequest orderRequest) {
        return orderServiceImpl.createOrder(orderRequest.getProductId(), orderRequest.getQuantity(), orderRequest.getTotalPrice(), orderRequest.getOrderDate(), orderRequest.getCustomerName(), orderRequest.getCustomerAddress(), orderRequest.getCustomerEmail());
    }

    @PutMapping("/edit-order/{id}")
    public ResponseEntity<Response> editOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
        return orderServiceImpl.editOrder(id, orderRequest.getProductId(), orderRequest.getQuantity(), orderRequest.getTotalPrice(), orderRequest.getOrderDate(), orderRequest.getStatus(), orderRequest.getCustomerName(), orderRequest.getCustomerAddress(), orderRequest.getCustomerEmail());
    }

    @DeleteMapping("/delete-order/{id}")
    public ResponseEntity<Response> deleteOrder(@PathVariable Long id) {
        return orderServiceImpl.deleteOrder(id);
    }
}
