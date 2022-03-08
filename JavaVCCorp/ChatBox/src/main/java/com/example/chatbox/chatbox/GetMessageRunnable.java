package com.example.chatbox.chatbox;

import org.springframework.util.ObjectUtils;

import java.io.IOException;

import static com.example.chatbox.client.ClientServerUtils.getData;

public class GetMessageRunnable implements Runnable {
    private String username;

    public GetMessageRunnable(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        while (true) {
            StringBuilder data = null;
            try {
                data = getData(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!ObjectUtils.isEmpty(data)) {
                System.out.println(data.toString());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
