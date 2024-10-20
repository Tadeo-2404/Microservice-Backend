package microservices.product_service.service.Implementation;

import org.springframework.stereotype.Service;

import microservices.product_service.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public String testService() {
        return "Success";
    }
}
