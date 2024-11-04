package microservices.order_service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import microservices.order_service.model.Order;


@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{
    Order findOrderById(Long id);
}
