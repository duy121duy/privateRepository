package com.example.chatbox.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface ClientSideService {
    public MultipartFile fileToMultipartFile(File file);

    public String storeFile(MultipartFile multipartFile) throws IOException;

    public Resource loadFileAsResource(String fileName);

    public boolean removeFileServer(String fileName);
}
