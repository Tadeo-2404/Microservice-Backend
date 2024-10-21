package microservices.inventory_service.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    private Long id;
    private String productId;
    private int quantity;
    private boolean inStock;
}
