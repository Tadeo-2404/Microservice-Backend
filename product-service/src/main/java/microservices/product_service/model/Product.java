package microservices.product_service.model;

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
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "productName")
    private String productName;
    @Column(name = "category")
    private String category;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Double price;
    @Column(name = "manufacturer")
    private String manufacturer;

    public Product(String productName, String category, String description, Double price, String manufacturer) {
        this.productName = productName;
        this.category = category;
        this.description = description;
        this.price = price;
        this.manufacturer = manufacturer;
    }
}
