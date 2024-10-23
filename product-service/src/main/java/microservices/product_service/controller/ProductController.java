package microservices.product_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservices.product_service.service.Implementation.ProductServiceImpl;

@RestController
@RequestMapping("/products/v1")
public class ProductController {
    private ProductServiceImpl productServiceImpl;

    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl =  productServiceImpl;
    }
    
    @GetMapping("/test")
    public String test() {
        return productServiceImpl.testService();
    }
}
