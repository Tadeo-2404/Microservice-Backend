package microservices.inventory_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservices.inventory_service.model.Inventory;
import microservices.inventory_service.service.Implementation.InventoryServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/inventory/v1")
public class InventoryController {
    private final InventoryServiceImpl inventoryServiceImpl;

    public InventoryController(InventoryServiceImpl inventoryServiceImpl) {
        this.inventoryServiceImpl = inventoryServiceImpl;
    }

    @GetMapping("/getInventory/{inventoryId}")
    public ResponseEntity<Inventory> findInventoryById(@PathVariable String inventoryId) {
        return ResponseEntity.ok(inventoryServiceImpl.findInventoryById(Long.parseLong(inventoryId)));
    }
}
