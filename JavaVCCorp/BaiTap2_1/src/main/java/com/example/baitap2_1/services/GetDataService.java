package com.example.baitap2_1.services;


import com.example.baitap2_1.thread.GetDataThread;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GetDataService {
    public void testBai1() throws InterruptedException {
        GetDataThread getDataThread = new GetDataThread();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(getDataThread, 0, 2, TimeUnit.SECONDS);
        Thread.sleep(300000);
        scheduledExecutorService.shutdown();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GetDataService getDataService = new GetDataService();
        getDataService.testBai1();
    }
}
