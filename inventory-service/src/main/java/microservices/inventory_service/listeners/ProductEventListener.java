package microservices.inventory_service.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import microservices.inventory_service.service.Implementation.InventoryServiceImpl;

@Slf4j
@Component
public class ProductEventListener {
    @Autowired
    private InventoryServiceImpl inventoryServiceImpl;

    @KafkaListener(topics = "product-created")
    public void handleProductCreated(String productId) {
        try {
            inventoryServiceImpl.createInventory(productId, 1, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "product-deleted")
    public void handleProductDeleted(String productId) {
        try {
            inventoryServiceImpl.deleInventoryByProductId(productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
