package com.example.chatbox.chatbox;

import com.example.chatbox.entity.Inbox;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Scanner;

import static com.example.chatbox.client.ClientServerUtils.postWithJson;

public class ClientA {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();
        new Thread(new GetMessageRunnable("A")).start();
        while (true) {
            String message = scanner.nextLine();

            // add request parameters or form parameters
            String json = gson.toJson(new Inbox(message, "A", "B"));
            postWithJson("http://localhost:8888/sendMessage", json);
        }
    }
}
