package com.example.chatbox.service.impl;

import com.example.chatbox.service.ClientSideService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ClientSideServiceImpl implements ClientSideService {
    private final String SERVERDIR = "Folder2_7/Server";

    @Override
    public boolean removeFileServer(String fileName) {
        System.out.println(SERVERDIR + "/" + fileName);
        return new File(SERVERDIR + "/" + fileName).delete();
    }

    @Override
    public MultipartFile fileToMultipartFile(File file) {
        FileItem fileItem = createFileItem(file);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }

    private static FileItem createFileItem(File file) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem("textField", "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public String storeFile(MultipartFile multipartFile) throws IOException {
        Path folder = Paths.get(SERVERDIR);
        Files.copy(multipartFile.getInputStream(), folder.resolve(multipartFile.getOriginalFilename()));
        return StringUtils.cleanPath(multipartFile.getOriginalFilename());
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(SERVERDIR).resolve(fileName).normalize();
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
