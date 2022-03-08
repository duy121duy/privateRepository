package com.example.chatbox.client;

import com.example.chatbox.entity.VersionForm;
import com.example.chatbox.service.impl.ClientSideServiceImpl;
import com.google.gson.Gson;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class GetChangesRunnable implements Runnable {
    private boolean check;
    private Client client;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public GetChangesRunnable(Client client) {
        this.check = true;
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            VersionForm versionForm = null;
            URL url = null;
            HttpURLConnection connection = null;
            BufferedReader in = null;
            try {
                url = new URL("http://localhost:8888/getChangedFile/" + client.getVersion());
                connection = (HttpURLConnection) url.openConnection();
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    Gson gson = new Gson();
                    versionForm = gson.fromJson(inputLine, VersionForm.class);
                }

                in.close();
                if (!ObjectUtils.isEmpty(versionForm)) {
                    client.setVersion(versionForm.getVersion());
                    System.out.println(client.getVersion());
                    if (versionForm.getStringStringMap() != null) {
                        for (String fileName : versionForm.getStringStringMap().keySet()) {
                            if (!Objects.equals(versionForm.getStringStringMap().get(fileName), "remove")) {
                                client.downloadFile(fileName);
                            } else {
                                new File(client.getClientNumberLocation().getPath() + "/" + fileName).delete();
                            }
                        }
                    }
                    check = false;
                }
                Thread.sleep(500);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
