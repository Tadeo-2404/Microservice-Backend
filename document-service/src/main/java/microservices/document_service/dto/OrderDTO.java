package microservices.document_service.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private String productId;
    private int quantity;
    private Double totalPrice;
    private Date orderDate;
    private String status;
    private String customerName;
    private String customerAddress;
    private String customerEmail;
}