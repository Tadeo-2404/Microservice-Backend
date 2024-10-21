package microservices.inventory_service.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryRequest {
    private String productId;
    private int quantity;
    private boolean inStock;
}
