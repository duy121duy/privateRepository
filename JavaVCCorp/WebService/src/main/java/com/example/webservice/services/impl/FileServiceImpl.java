package com.example.webservice.services.impl;

import com.example.webservice.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {
    @Value("${file.upload.location}")
    private String location;

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        Path folder = Paths.get(location);
        Files.copy(file.getInputStream(), folder.resolve(file.getOriginalFilename()));
        return StringUtils.cleanPath(file.getOriginalFilename());
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        Path folder = Paths.get(location);
        try {
            Path filePath = folder.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                System.out.println("File not found " + fileName);
                return null;
            }
        } catch (MalformedURLException ex) {
            System.out.println("File not found " + fileName);
            return null;
        }
    }
}
