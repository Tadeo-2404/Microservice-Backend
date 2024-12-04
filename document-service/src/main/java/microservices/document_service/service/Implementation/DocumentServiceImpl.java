package microservices.document_service.service.Implementation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import microservices.document_service.dto.OrderDTO;
import microservices.document_service.model.Response;
import microservices.document_service.service.DocumentService;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

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
            // Create a request to list objects
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                    .bucket(BUCKET_NAME)
                    .build();

            // Fetch the response
            ListObjectsV2Response listedObjects = s3Client.listObjectsV2(request);

            // Get the list of objects
            List<S3Object> objects = listedObjects.contents();

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(objects);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> searchDocumentByFilename(String fileName) {
        Response response = new Response();
        try {
            // Build the HeadObjectRequest
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(fileName)
                    .build();

            // Make the HeadObject call
            HeadObjectResponse result = s3Client.headObject(headObjectRequest);

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
    public ResponseEntity<Response> searchDocumentByExtension(String extension) {
        Response response = new Response();
        try {
            // Create a request to list objects
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                    .bucket(BUCKET_NAME)
                    .build();

            // Fetch the response
            ListObjectsV2Response result = s3Client.listObjectsV2(request);

            // Filter objects by extension
            List<S3Object> filteredObjects = result.contents().stream()
                    .filter(obj -> obj.key().toLowerCase().endsWith(extension.toLowerCase()))
                    .collect(Collectors.toList());

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse(filteredObjects);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> deleteDocument(String fileName) {
        Response response = new Response();
        try {
            s3Client.deleteBucket(request -> request.bucket(fileName));

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse("Bucket Deleted Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> downloadDocument(String fileName) {
        Response response = new Response();
        try {
            Path downloadPath = Paths.get("/Downloads");

            s3Client.getObject(request -> request
                    .bucket(BUCKET_NAME)
                    .key(fileName),
                    ResponseTransformer.toFile(downloadPath));

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse("File downloaded successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Response> uploadDocument(String fileName, String pathFile) {
        Response response = new Response();
        try {
            File file = new File(pathFile);

            s3Client.putObject(request -> 
            request
              .bucket(BUCKET_NAME)
              .key(file.getName())
              .ifNoneMatch("*"), 
            file.toPath());

            response.setStatus(200);
            response.setMessage("Success");
            response.setResponse("File uploaded successfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Failed to upload file: " + e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
