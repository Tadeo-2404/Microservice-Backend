package microservices.inventory_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservices.inventory_service.model.Response;
import microservices.inventory_service.service.Implementation.InventoryServiceImpl;
import microservices.inventory_service.utils.InventoryRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/inventory/v1")
public class InventoryController {
    private final InventoryServiceImpl inventoryServiceImpl;

    @Autowired
    public InventoryController(InventoryServiceImpl inventoryServiceImpl) {
        this.inventoryServiceImpl = inventoryServiceImpl;
    }

    @GetMapping("/getInventory/{inventoryId}")
    public ResponseEntity<Response> findInventoryById(@PathVariable String inventoryId) {
        return inventoryServiceImpl.findInventoryById(inventoryId);
    }

    @PostMapping("/create-inventory")
    public ResponseEntity<Response> createInventory(@RequestBody InventoryRequest inventoryRequest) {
        return inventoryServiceImpl.createInventory(inventoryRequest.getProductId(), inventoryRequest.getQuantity(), inventoryRequest.isInStock());
    }
}
