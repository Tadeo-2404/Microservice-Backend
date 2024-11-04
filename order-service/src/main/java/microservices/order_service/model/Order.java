package microservices.order_service.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "productId", nullable = false)
    private String productId; 
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "totalPrice", nullable = false)
    private Double totalPrice;
    @Column(name = "orderDate", nullable = false)
    private Timestamp orderDate;
    @Column(name = "status", nullable = false)
    private String status; 
    @Column(name = "customerName")
    private String customerName;
    @Column(name = "customerAddress")
    private String customerAddress;
    @Column(name = "customerEmail")
    private String customerEmail;

    public Order(String productId, int quantity, Double totalPrice, Timestamp orderDate, String customerName, String customerAddress, String customerEmail) {
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = "pending";
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
    }
}
