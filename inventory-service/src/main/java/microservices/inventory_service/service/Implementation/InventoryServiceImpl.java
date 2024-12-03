package microservices.inventory_service.service.Implementation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import microservices.inventory_service.model.Inventory;
import microservices.inventory_service.model.Response;
import microservices.inventory_service.repository.InventoryRepository;
import microservices.inventory_service.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<Response> findInventoryById(String id) {
        Response response = new Response();
        try {
            Inventory inventory = inventoryRepository.findInventoryById(id);

            if(inventory == null) {
                response.setStatus(404);
                response.setMessage("No data found");
                response.setResponse(inventory);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(inventory);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Response> createInventory(String productId, int quantity, boolean inStock) {
        Response response = new Response();
        try {
            if(productId == null) {
                response.setStatus(400);
                response.setMessage("Missing data");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Inventory inventory = new Inventory(productId, quantity, inStock);
            inventoryRepository.save(inventory);

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(inventory);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Response> editInventory(String id, String productId, int quantity, boolean inStock) {
        Response response = new Response();
        try {
            if(id == null || productId == null || quantity == 0) {
                response.setStatus(400);
                response.setMessage("Missing data");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Inventory inventoryExist = inventoryRepository.findInventoryById(id);
            if(inventoryExist == null) {
                response.setStatus(404);
                response.setMessage("Not found");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Inventory inventory = new Inventory(id, productId, quantity, inStock);
            inventoryRepository.save(inventory);

            response.setStatus(200);
            response.setMessage("Inventory edited successfully");
            response.setResponse(inventory);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Response> deleInventory(String id) {
        Response response = new Response();
        try {
            if(id == null) {
                response.setStatus(400);
                response.setMessage("Missing data");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Inventory inventoryExist = inventoryRepository.findInventoryById(id);
            if(inventoryExist == null) {
                response.setStatus(404);
                response.setMessage("Not found");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            inventoryRepository.deleteById(id);

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse("Inventory deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Response> getAllInventories() {
        Response response = new Response();
        try {
            Iterable<Inventory> result = inventoryRepository.findAll();
            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Response> deleInventoryByProductId(String productId) {
        Response response = new Response();
        try {
            if(productId == null) {
                response.setStatus(400);
                response.setMessage("Missing data");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            
            inventoryRepository.deleteInventoryByProductId(productId);

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse("Inventory deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }   
} 
