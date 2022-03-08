package com.example.chatbox.controller;

import com.example.chatbox.entity.Version;
import com.example.chatbox.entity.VersionForm;
import com.example.chatbox.service.ClientSideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

@Controller
public class ClientServerController {
    private static int VERSION = 0;

    public static Map<Integer, Map<File, String>> VERSIONSERVERDIRS = new ConcurrentHashMap<>();

    private final ClientSideService clientSideService;

    @Autowired
    public ClientServerController(ClientSideService clientSideService) {
        File[] files = new File("Folder2_7/Server").listFiles();
        for (File f : files) {
            f.delete();
        }
        this.clientSideService = clientSideService;
    }

    @GetMapping("/getChangedFile/{ver}")
    public ResponseEntity<VersionForm> getChangedFile(@PathVariable("ver") int ver) {
        Map<String, String> result = null;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Map<String, String>> mapFuture = executorService.submit(new LongPollingRunnable(ver));
        executorService.shutdownNow();
        try {
            result = mapFuture.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new VersionForm(VERSION, result), HttpStatus.OK);
    }

    @PostMapping("/notifyChanges")
    public ResponseEntity<Integer> notifyChange(@RequestBody Version version) {
        VERSIONSERVERDIRS.put(++VERSION, version.getFileStringMap());
        System.out.println(VERSION);
        for (File s : version.getFileStringMap().keySet()) {
            System.out.println(s + " " + version.getFileStringMap().get(s));
            if (Objects.equals(version.getFileStringMap().get(s), "remove")) {
                System.out.println(clientSideService.removeFileServer(s.getName()));
            } else if (Objects.equals(version.getFileStringMap().get(s), "add")) {
                try {
                    clientSideService.storeFile(clientSideService.fileToMultipartFile(s));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                clientSideService.removeFileServer(s.getName());
                try {
                    clientSideService.storeFile(clientSideService.fileToMultipartFile(s));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResponseEntity<>(VERSION, HttpStatus.OK);
    }

    private static class LongPollingRunnable implements Callable<Map<String, String>> {
        private int version;

        public LongPollingRunnable(int version) {
            this.version = version;
        }

        @Override
        public Map<String, String> call() throws Exception {
            while (VERSION == version) {
                Thread.sleep(500);
            }
            Map<String, String> stringStringMap = new HashMap<>();
            for (int i = ++version; i <= VERSION; i++) {
                Map<File, String> fileStringMap = VERSIONSERVERDIRS.get(i);
                for (File s : fileStringMap.keySet()) {
                    stringStringMap.put(s.getName(), fileStringMap.get(s));
                }
            }
            return stringStringMap;
        }
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = clientSideService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            System.out.println(resource.getFile().getAbsolutePath());
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
