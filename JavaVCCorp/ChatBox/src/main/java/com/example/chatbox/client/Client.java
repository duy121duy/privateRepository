package com.example.chatbox.client;

import com.example.chatbox.entity.Version;
import com.example.chatbox.service.impl.ClientSideServiceImpl;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.example.chatbox.client.ClientServerUtils.postWithJson;

public class Client {

    private final String CLIENTLOCATION = "Folder2_7/Client";

    private final File clientNumberLocation;

    private static Map<File, Long> FILEDIR = new HashMap<>();

    public int version = 0;

    private final String clientNumber;

    public File getClientNumberLocation() {
        return clientNumberLocation;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    private ClientSideServiceImpl clientSideService = new ClientSideServiceImpl();

    public Client(String clientNumber) {
        this.clientNumber = clientNumber;
        clientNumberLocation = new File(CLIENTLOCATION + "/" + clientNumber);
        if (clientNumberLocation.listFiles() != null) {
            for (File f : clientNumberLocation.listFiles()) {
                f.delete();
            }
        }
    }

    public void downloadFile(String fileName) throws IOException {
        URL fetchWebsite = new URL("http://localhost:8888/downloadFile/" + fileName);
        File file = new File(clientNumberLocation + "/" + fileName);
        FileUtils.copyURLToFile(fetchWebsite, file);
    }

    public Map<File, Long> getCurrentFileDir() {
        Map<File, Long> currentFileDir = new HashMap<>();
        for (File f : Objects.requireNonNull(clientNumberLocation.listFiles())) {
            currentFileDir.put(f, f.lastModified());
        }
        return currentFileDir;
    }

    public Map<File, String> getChangedFile() {
        Map<File, String> fileStringMap = new HashMap<>();
        Map<File, Long> currentFileDir = getCurrentFileDir();
        if (!CollectionUtils.isEmpty(currentFileDir)) {
            for (File f : currentFileDir.keySet()) {
                if (FILEDIR.containsKey(f)) {
                    if (!Objects.equals(currentFileDir.get(f), FILEDIR.get(f))) {
                        fileStringMap.put(f, "modify");
                    }
                } else {
                    fileStringMap.put(f, "add");
                }
                FILEDIR.put(f, f.lastModified());
            }
        }

        List<File> fileList = new ArrayList<>();

        for (File f : FILEDIR.keySet()) {
            if (!currentFileDir.containsKey(f)) {
                fileStringMap.put(f, "remove");
                fileList.add(f);
            }
        }

        if (!CollectionUtils.isEmpty(fileList)) {
            for (File f : fileList) {
                FILEDIR.remove(f);
            }
        }

        return fileStringMap;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client(args[0]);
        Gson gson = new Gson();
        GetChangesRunnable getChangesRunnable = new GetChangesRunnable(client);
        new Thread(getChangesRunnable).start();
        while (true) {
            Map<File, String> fileStringMap = client.getChangedFile();
            if (fileStringMap.size() != 0 && getChangesRunnable.isCheck()) {
                // add request parameters or form parameters
                String json = gson.toJson(new Version(client.getVersion(), fileStringMap));
                client.version = Integer.parseInt(postWithJson("http://localhost:8888/notifyChanges", json));
                System.out.println(client.version);
            } else {
                getChangesRunnable.setCheck(true);
            }
            Thread.sleep(500);
        }
    }
}
