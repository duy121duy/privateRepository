package com.example.webservice.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    public String storeFile(MultipartFile file) throws IOException;

    public Resource loadFileAsResource(String fileName);
}
