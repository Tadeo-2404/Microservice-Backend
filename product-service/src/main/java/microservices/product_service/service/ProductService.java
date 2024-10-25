package microservices.product_service.service;

import org.springframework.http.ResponseEntity;

import microservices.product_service.model.Response;

public interface ProductService {
    ResponseEntity<Response> createProduct(String productName, String category, String description, Double price, String manufacturer);
    ResponseEntity<Response> getAllProducts();
    ResponseEntity<Response> searchProductByIdLong(Long id);
    ResponseEntity<Response> searchProductByName(String productName);
    ResponseEntity<Response> searchProductByManufacturer(String manufacturer);
    ResponseEntity<Response> searchProductByPrice(Double price);
    ResponseEntity<Response> editProduct(Long id, String productName, String category, String description, Double price, String manufacturer);
    ResponseEntity<Response> eliminateProduct(Long id);
} 