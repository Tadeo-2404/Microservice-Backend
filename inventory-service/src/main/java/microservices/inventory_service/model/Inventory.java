package microservices.inventory_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "inventory")
public class Inventory {
    @Id
    private String id;
    private String productId;
    private int quantity;
    private boolean inStock;

    public Inventory(String productId, int quantity, boolean inStock) {
        this.productId = productId;
        this.quantity = quantity;
        this.inStock = inStock;
    }
}
