package com.compiledideas.crewsecback.storage;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${storage.location}")
    private String storageLocation;

    private void initializeStorage() {
        Path storagePath = Paths.get(storageLocation);
        try {
            if (!Files.exists(storagePath)) {
                Files.createDirectories(storagePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage directory", e);
        }
    }

    public String storeFile(MultipartFile file, String location) {
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        try {
            Path filePath = Paths.get(storageLocation + location, uniqueFileName);
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + originalFileName, e);
        }

        return uniqueFileName;
    }

    public byte[] getFile(String fileName, String location ) {
        try {
            Path filePath = Paths.get(storageLocation + location, fileName);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve file: " + fileName, e);
        }
    }

    public void deleteFile(String fileName, String location) {
        try {
            Path filePath = Paths.get(storageLocation + location, fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + fileName, e);
        }
    }
}
