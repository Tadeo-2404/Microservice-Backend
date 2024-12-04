package microservices.document_service.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import microservices.document_service.dto.OrderDTO;
import microservices.document_service.model.Response;

public interface DocumentService {
    ResponseEntity<byte[]>  createDocument(OrderDTO orderDTO);
    ResponseEntity<Response> getAllDocuments();
    ResponseEntity<Response> searchDocumentByTitle(String fileName);
    ResponseEntity<Response> searchDocumentByExtension(String extension);
    ResponseEntity<Response> deleteDocument(String fileName);
    ResponseEntity<Response> downloadDocument(String fileName);
    ResponseEntity<Response> uploadDocument(@RequestParam String fileName, @RequestParam MultipartFile file);
} 