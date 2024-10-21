package microservices.inventory_service.service.Implementation;

import org.springframework.stereotype.Service;

import microservices.inventory_service.model.Inventory;
import microservices.inventory_service.repository.InventoryRepository;
import microservices.inventory_service.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory findInventoryById(Long id) {
        try {
            return inventoryRepository.findInventoryById(id);
        } catch (Exception e) {
            return null;
        }
    }   
}
