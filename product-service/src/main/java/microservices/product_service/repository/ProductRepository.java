package microservices.product_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import microservices.product_service.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.productName = :productName")
    Optional<Product> getProductByProductName(@Param("productName")String productName);
    @Query("SELECT p FROM Product p WHERE p.manufacturer = :manufacturer")
    Optional<Product> getProductByManufacturer(@Param("manufacturer")String manufacturer);
    @Query("SELECT p FROM Product p WHERE p.price = :price")
    Optional<Product> getProductByPrice(@Param("price") Double price);    
} 