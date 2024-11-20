package microservices.product_service.service.Implementation;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import microservices.product_service.model.Product;
import microservices.product_service.model.Response;
import microservices.product_service.repository.ProductRepository;
import microservices.product_service.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final KafkaTemplate<String,String> kafkaTemplate;

    public ProductServiceImpl(ProductRepository productRepository, KafkaTemplate kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ResponseEntity<Response> createProduct(String productName, String category, String description, Double price,
            String manufacturer) {
        Response response = new Response();

        try {
            if (productName == null || category == null || description == null || price == null || manufacturer == null) {
                response.setStatus(400);
                response.setMessage("Missing data");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Product product = new Product(productName, category, description, price, manufacturer);
            Product productSaved = productRepository.save(product);
            kafkaTemplate.send("product-created", productSaved.getId().toString());

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> searchProductByIdLong(Long id) {
        Response response = new Response();

        try {
            if (id == null) {
                response.setStatus(400);
                response.setMessage("Missing data");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<Product> product = productRepository.findById(id);
            if(product.isEmpty()) {
                response.setStatus(404);
                response.setMessage("No data found");
                response.setResponse(null);
            }

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> searchProductByName(String productName) {
        Response response = new Response();

        try {
            if (productName == null) {
                response.setStatus(400);
                response.setMessage("Missing data");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<Product> product = productRepository.getProductByProductName(productName);
            if(product.isEmpty()) {
                response.setStatus(404);
                response.setMessage("No data found");
                response.setResponse(null);
            }

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> searchProductByManufacturer(String manufacturer) {
        Response response = new Response();

        try {
            if (manufacturer == null) {
                response.setStatus(400);
                response.setMessage("Missing data");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<Product> product = productRepository.getProductByManufacturer(manufacturer);
            if(product.isEmpty()) {
                response.setStatus(404);
                response.setMessage("No data found");
                response.setResponse(null);
            }

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> editProduct(Long id, String productName, String category, String description, Double price,
            String manufacturer) {
        Response response = new Response();

        try {
            if (id == null &&  (productName == null && category == null && description == null && price == null && manufacturer == null)) {
                response.setStatus(400);
                response.setMessage("Missing data");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<Product> productById = productRepository.findById(id);
            if(productById.isEmpty()) {
                response.setStatus(404);
                response.setMessage("Product not found");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Optional<Product> product = productRepository.getProductByProductName(productName);
            if(product.isPresent()) {
                response.setStatus(400);
                response.setMessage("Name already in use");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Product productToEdit = new Product(id, productName, category, description, price, manufacturer);
            productRepository.save(productToEdit);

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> eliminateProduct(Long id) {
        Response response = new Response();

        try {
            if (id == null) {
                response.setStatus(400);
                response.setMessage("Missing data");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<Product> product = productRepository.findById(id);
            if(product.isEmpty()) {
                response.setStatus(404);
                response.setMessage("No data found");
                response.setResponse(null);
            }

            productRepository.delete(new Product(id, null, null, null, null, null));

            response.setStatus(200);
            response.setMessage("Product deleted successfully");
            response.setResponse("Successful operation");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> getAllProducts() {
        Response response = new Response();

        try {
            Iterable<Product> products = productRepository.findAll();

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(products);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> searchProductByPrice(Double price) {
        Response response = new Response();

        try {
            Optional<Product> products = productRepository.getProductByPrice(price);

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(products);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
