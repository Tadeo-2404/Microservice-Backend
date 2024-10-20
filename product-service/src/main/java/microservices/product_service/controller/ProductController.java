package microservices.product_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservices.product_service.service.Implementation.ProductServiceImpl;

@RestController
@RequestMapping("/products/v1")
public class ProductController {
    private ProductServiceImpl productServiceImpl;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl =  productServiceImpl;
    }
    
    @GetMapping("/test")
    public String test() {
        return productServiceImpl.testService();
    }
}
