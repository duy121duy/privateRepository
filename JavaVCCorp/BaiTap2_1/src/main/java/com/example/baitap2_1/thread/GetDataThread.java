package com.example.baitap2_1.thread;


import com.example.baitap2_1.entities.Data;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDataThread implements Runnable {
    final static Logger logger = LogManager.getLogger("duy");

    private static int oldUser = -1;

    private static int count = 0;

    public Data getData() throws IOException {
        Data data = null;
        URL url = new URL("http://news.admicro.vn:10002/api/realtime?domain=kenh14.vn");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            Gson gson = new Gson();
            data = gson.fromJson(inputLine, Data.class);
        }
        in.close();
        return data;
    }

    @Override
    public void run() {
        try {
            int newUser = getData().getUser();
            if (oldUser == -1) {
                logger.info(newUser);
                oldUser = newUser;
            } else {
                if (Math.abs((newUser - oldUser) / (oldUser * 1.0)) >= 0.002) {
                    logger.info(newUser);
                    oldUser = newUser;
                    count = 0;
                } else {
                    if (count == 5) {
                        logger.debug(newUser);
                        oldUser = newUser;
                        count = 0;
                    } else {
                        System.out.println("No log");
                        count++;
                    }
                }
            }
            Thread.sleep(2000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
