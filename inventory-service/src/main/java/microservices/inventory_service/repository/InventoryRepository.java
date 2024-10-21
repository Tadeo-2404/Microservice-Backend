package microservices.inventory_service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import microservices.inventory_service.model.Inventory;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long>{
    Inventory findInventoryById(Long id);
}
