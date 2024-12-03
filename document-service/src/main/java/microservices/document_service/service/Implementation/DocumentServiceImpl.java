package microservices.document_service.service.Implementation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import microservices.document_service.dto.OrderDTO;
import microservices.document_service.model.Response;
import microservices.document_service.service.DocumentService;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private S3Client s3Client;

    private static final String BUCKET_NAME = "springbootbucketproject";

    @Override
    public ResponseEntity<byte[]> createDocument(OrderDTO orderDTO) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Create a new Excel workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Order Report");

            // Define the column headers
            String[] columnHeaders = { "ID", "Product ID", "Quantity", "Total Price", "Order Date", "Status",
                    "Customer Name", "Customer Address", "Customer Email" };

            // Create the header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnHeaders.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnHeaders[i]);
            }

            // Create a row to populate the data (row index 1 as header is in row 0)
            Row row = sheet.createRow(1);

            // Add data to cells
            row.createCell(0).setCellValue(orderDTO.getId().toString());
            row.createCell(1).setCellValue(orderDTO.getProductId());
            row.createCell(2).setCellValue(orderDTO.getQuantity());
            row.createCell(3).setCellValue(orderDTO.getTotalPrice());
            row.createCell(4).setCellValue(orderDTO.getOrderDate().toString());
            row.createCell(5).setCellValue(orderDTO.getStatus());
            row.createCell(6).setCellValue(orderDTO.getCustomerName());
            row.createCell(7).setCellValue(orderDTO.getCustomerAddress());
            row.createCell(8).setCellValue(orderDTO.getCustomerEmail());

            // Auto-size columns for better readability
            for (int i = 0; i < columnHeaders.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the workbook to the output stream
            workbook.write(outputStream);
            workbook.close();

            // Prepare the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order_report.xlsx");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            // Return the file as a byte array
            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating the Excel document: " + e.getMessage()).getBytes());
        }
    }

    @Override
    public ResponseEntity<Response> getAllDocuments() {
        Response response = new Response();
        try {

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> searchDocumentByTitle(String title) {
        Response response = new Response();
        try {

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> searchDocumentByExtension(String extension) {
        Response response = new Response();
        try {

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> editDocument(String title) {
        Response response = new Response();
        try {

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> deleteDocument(String title) {
        Response response = new Response();
        try {

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> downloadDocument() {
        Response response = new Response();
        try {

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Response> uploadDocument(String fileName, MultipartFile file) {
        Response response = new Response();
        try {
            String contentType = file.getContentType();

            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(fileName) 
                    .contentType(contentType)
                    .build(),
                    RequestBody.fromBytes(file.getBytes()) 
            );

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse("File uploaded successfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            response.setStatus(500);
            response.setMessage("Failed to upload file: " + e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
