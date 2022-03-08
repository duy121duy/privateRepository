package com.example.chatbox.client.clients;

import com.example.chatbox.client.Client;
import com.example.chatbox.client.GetChangesRunnable;
import com.example.chatbox.entity.Version;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.example.chatbox.client.ClientServerUtils.postWithJson;

public class Client1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client("1");
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
