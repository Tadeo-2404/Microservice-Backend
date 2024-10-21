package microservices.inventory_service.service;

import microservices.inventory_service.model.Inventory;

public interface InventoryService {
    public Inventory findInventoryById(Long id);   
}
