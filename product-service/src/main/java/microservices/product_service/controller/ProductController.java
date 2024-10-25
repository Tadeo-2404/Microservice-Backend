package microservices.product_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import microservices.product_service.model.Response;
import microservices.product_service.service.Implementation.ProductServiceImpl;
import microservices.product_service.utils.ProductRequest;

@RestController
@RequestMapping("/products/v1")
public class ProductController {
    private ProductServiceImpl productServiceImpl;

    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl =  productServiceImpl;
    }
    
    @GetMapping("/products")
    public ResponseEntity<Response> getProduct(
        @RequestParam String id, @RequestParam String productName, @RequestParam String manufacturer, @RequestParam String price
    ) {
        if(id != null) {
            return productServiceImpl.searchProductByIdLong(Long.parseLong(id));
        } else if(productName != null) {
            return productServiceImpl.searchProductByName(productName);
        } else if(manufacturer != null) {
            return productServiceImpl.searchProductByManufacturer(manufacturer);
        } else if(price != null) {
            return productServiceImpl.searchProductByPrice(Double.parseDouble(price));
        }
 
        return productServiceImpl.getAllProducts();
    }

    @PostMapping("/create-product")
    public ResponseEntity<Response> createProduct(ProductRequest productRequest) {
        return productServiceImpl.createProduct(productRequest.getProductName(), productRequest.getCategory(), productRequest.getDescription(), productRequest.getPrice(), productRequest.getManufacturer());
    }

    @PutMapping("/edit-product")
    public ResponseEntity<Response> editProduct(ProductRequest productRequest) {
        return productServiceImpl.editProduct(productRequest.getId() ,productRequest.getProductName(), productRequest.getCategory(), productRequest.getDescription(), productRequest.getPrice(), productRequest.getManufacturer());
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<Response> deleteProduct(ProductRequest productRequest) {
        return productServiceImpl.eliminateProduct(productRequest.getId());
    }
}
