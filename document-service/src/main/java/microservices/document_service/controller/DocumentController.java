package microservices.document_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import microservices.document_service.dto.OrderDTO;
import microservices.document_service.service.Implementation.DocumentServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/documents/v1")
public class DocumentController {
    private DocumentServiceImpl documentServiceImpl;

    public DocumentController(DocumentServiceImpl documentServiceImpl) {
        this.documentServiceImpl =  documentServiceImpl;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam String fileName, @RequestParam MultipartFile file) {
        documentServiceImpl.uploadDocument(fileName, file);
        return "File uploaded successfully!";
    }

    @PostMapping("/create-document")
    public ResponseEntity<byte[]> createDocument(@RequestBody OrderDTO orderDTO) {
        return documentServiceImpl.createDocument(orderDTO);
    }    
}
