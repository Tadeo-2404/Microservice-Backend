package microservices.order_service.utils;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderRequest {
    private Long id;
    private String productId; 
    private int quantity;
    private Double totalPrice;
    private Timestamp orderDate;
    private String status; 
    private String customerName;
    private String customerAddress;
    private String customerEmail;
}
