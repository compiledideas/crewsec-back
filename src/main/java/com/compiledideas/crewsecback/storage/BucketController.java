package com.compiledideas.crewsecback.storage;

import com.compiledideas.crewsecback.utils.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/storage")
public class BucketController {

    private final StorageService service;
    private final Logger logger = LogManager    .getLogger(BucketController.class);

    @Autowired
    public BucketController(StorageService service) {
        this.service = service;
    }

    @PostMapping(value = "/docs/upload", consumes = "multipart/form-data")
    public ResponseEntity<Object> uploadFile(@RequestPart(value = "file") MultipartFile file, HttpServletRequest request) {

        var link = service.storeFile(file, "/docs");
        return ResponseHandler.generateResponse(
                "Image uploaded to bucket successfully",
                HttpStatus.OK,
                Utils.getBaseUrl(request) + "/api/v1/storage/docs/download/" + link
        );
    }

    @PostMapping(value = "/images/upload", consumes = "multipart/form-data")
    public ResponseEntity<Object> uploadImage(@RequestPart(value = "file") MultipartFile file,  HttpServletRequest request) {

        var link = service.storeFile(file, "/images");
        logger.info("Uploaded doc file {}", link);
        return ResponseHandler.generateResponse(
                "document uploaded to bucket successfully",
                HttpStatus.OK,
                Utils.getBaseUrl(request) + "/api/v1/storage/images/download/" + link
        );
    }

    @GetMapping(
            value = "/images/download/{imageName}",
            produces = "image/*"
    )
    public Object downloadImage(@PathVariable String imageName) {

        return service.getFile(imageName, "/images");
    }

    @GetMapping(value = "/docs/download/{imageName}", produces = "*/*")
    public Object downloadResumeDocument(@PathVariable String imageName) {

        return service.getFile(imageName, "/docs");
    }

    @DeleteMapping("/images/delete/{fileName}")
    public ResponseEntity<Object> deleteImage( @PathVariable String fileName) {
        service.deleteFile(fileName, "/images");
        return ResponseHandler.generateResponse(
                "Image removed from bucket successfully",
                HttpStatus.OK,
                null
        );
    }

    @DeleteMapping("/docs/delete/{fileName}")
    public ResponseEntity<Object> deleteFile( @PathVariable String fileName) {
        service.deleteFile(fileName, "/docs");
        return ResponseHandler.generateResponse(
                "Document removed from bucket successfully",
                HttpStatus.OK,
                null
        );
    }
}
