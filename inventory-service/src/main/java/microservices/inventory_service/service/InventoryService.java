package microservices.inventory_service.service;

import org.springframework.http.ResponseEntity;

import microservices.inventory_service.model.Response;

public interface InventoryService {
    public ResponseEntity<Response> createInventory(String productId, int quantity, boolean inStock);
    public ResponseEntity<Response> editInventory(String id, String productId, int quantity, boolean inStock);
    public ResponseEntity<Response> deleInventory(String id);
    public ResponseEntity<Response> deleInventoryByProductId(String productId);
    public ResponseEntity<Response> findInventoryById(String id);   
    public ResponseEntity<Response> getAllInventories();
}
