package microservices.inventory_service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import microservices.inventory_service.model.Inventory;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, String>{
    Inventory findInventoryById(String id);
}
