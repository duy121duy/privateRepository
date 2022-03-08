package com.example.chatbox.controller;

import com.example.chatbox.entity.Inbox;
import com.example.chatbox.service.impl.ClientSideServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ChatBoxController {
    public static Map<String, List<String>> HASHMAP = new HashMap<>();

    private final ClientSideServiceImpl clientSideServiceImpl;

    @Autowired
    public ChatBoxController(ClientSideServiceImpl clientSideServiceImpl) {
        this.clientSideServiceImpl = clientSideServiceImpl;
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody Inbox inbox) {
        if (HASHMAP.containsKey(inbox.getUsernameReceiver())) {
            HASHMAP.get(inbox.getUsernameReceiver()).add(inbox.getUsername() + " - " + inbox.getMessage());
        } else {
            HASHMAP.put(inbox.getUsernameReceiver(), Stream.of(inbox.getUsername() + " - " + inbox.getMessage()).collect(Collectors.toList()));
        }
        return new ResponseEntity<>(inbox.getMessage(), HttpStatus.OK);
    }

    @GetMapping("/getMessage")
    public ResponseEntity<String> getMessage(@RequestParam("username") String username) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        String result = null;
        try {
            Future<String> stringFuture = executorService.submit(new LongPollingRunnable(username));
            executorService.shutdownNow();
            result = stringFuture.get(1, TimeUnit.SECONDS);
            HASHMAP.remove(username);
        } catch (ExecutionException | TimeoutException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private static class LongPollingRunnable implements Callable<String> {
        private String username;

        public LongPollingRunnable(String username) {
            this.username = username;
        }

        @Override
        public String call() throws Exception {
            StringBuilder result = null;
            while (!HASHMAP.containsKey(username)) {
                Thread.sleep(500);
            }
            result = new StringBuilder();
            for (String s : HASHMAP.get(username)) {
                result.append(s).append("\n");
            }
            return result.toString();
        }
    }
}
