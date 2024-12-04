package microservices.document_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import microservices.document_service.dto.OrderDTO;
import microservices.document_service.model.Response;
import microservices.document_service.service.Implementation.DocumentServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/documents/v1")
public class DocumentController {
    private DocumentServiceImpl documentServiceImpl;

    public DocumentController(DocumentServiceImpl documentServiceImpl) {
        this.documentServiceImpl =  documentServiceImpl;
    }

    @GetMapping("/")
    public ResponseEntity<Response> getAllDocuments() {
        return documentServiceImpl.getAllDocuments();
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Response> getDocumentByTitle(@PathVariable String fileName) {
        return documentServiceImpl.searchDocumentByFilename(fileName);
    }
    
    @GetMapping("/getByExtension")
    public ResponseEntity<Response> getDocumentByExtension(@RequestParam String extension) {
        return documentServiceImpl.searchDocumentByFilename(extension);
    }   

    @GetMapping("/download-document")
    public ResponseEntity<Response> downloadDocument(String fileName) {
        return documentServiceImpl.downloadDocument(fileName);
    }

    @PostMapping("/upload-document")
    public ResponseEntity<Response> uploadFile(@RequestParam String fileName, @RequestParam String pathfile) {
        return documentServiceImpl.uploadDocument(fileName, pathfile);
    }

    @PostMapping("/create-document")
    public ResponseEntity<byte[]> createDocument(@RequestBody OrderDTO orderDTO) {
        return documentServiceImpl.createDocument(orderDTO);
    }    

    @DeleteMapping("/delete-document")
    public ResponseEntity<Response> deleteDocument(@RequestParam String fileName) {
        return documentServiceImpl.deleteDocument(fileName);
    }
}
