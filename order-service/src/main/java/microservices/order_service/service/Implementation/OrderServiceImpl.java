package microservices.order_service.service.Implementation;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import microservices.order_service.model.Order;
import microservices.order_service.model.Response;
import microservices.order_service.repository.OrderRepository;
import microservices.order_service.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public ResponseEntity<Response> findOrderById(Long id) {
        Response response = new Response();
        try {
            Order order = orderRepository.findOrderById(id);

            if(order == null) {
                response.setStatus(404);
                response.setMessage("No data found");
                response.setResponse(order);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(order);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> createOrder(String productId, int quantity, Double totalPrice, Timestamp orderDate, String customerName, String customerAddress, String customerEmail) {
        Response response = new Response();
        try {
            if(productId == null && quantity == 0 && totalPrice == null && orderDate == null && customerName == null && customerAddress == null && customerEmail == null) {
                response.setStatus(400);
                response.setMessage("Missing data");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Order order = new Order(productId, quantity, totalPrice, orderDate, customerName, customerAddress, customerEmail);
            orderRepository.save(order);

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(order);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> editOrder(Long id, String productId, int quantity, Double totalPrice, Timestamp orderDate, String status, String customerName, String customerAddress, String customerEmail) {
        Response response = new Response();
        try {
            if(id == 0 && productId == null && quantity == 0 && totalPrice == null && orderDate == null && status == null && customerName == null && customerAddress == null && customerEmail == null) {
                response.setStatus(400);
                response.setMessage("Missing data");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Order OrderExist = orderRepository.findOrderById(id);
            if(OrderExist == null) {
                response.setStatus(404);
                response.setMessage("Not found");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Order order = new Order(id, productId, quantity, totalPrice, orderDate, status, customerName, customerAddress, customerEmail);
            orderRepository.save(order);

            response.setStatus(200);
            response.setMessage("Order edited successfully");
            response.setResponse(order);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> deleteOrder(Long id) {
        Response response = new Response();
        try {
            if(id == null) {
                response.setStatus(400);
                response.setMessage("Missing data");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Order OrderExist = orderRepository.findOrderById(id);
            if(OrderExist == null) {
                response.setStatus(404);
                response.setMessage("Not found");
                response.setResponse(null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            orderRepository.deleteById(id);

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse("Order deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> getAllOrders() {
        Response response = new Response();
        try {
            Iterable<Order> result = orderRepository.findAll();
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
}
