package microservices.product_service.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRequest {
    private Long id;
    private String productName;
    private String category;
    private String description;
    private Double price;
    private String manufacturer;
}
