package microservices.inventory_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import microservices.inventory_service.model.Inventory;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String>{
    Inventory findInventoryById(String id);
    void deleteInventoryByProductId(String productId);
}
