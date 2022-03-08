package com.example.kafkaspring.controller;

import com.example.kafkaspring.model.MessageRequest;
import com.example.kafkaspring.model.User;
import com.example.kafkaspring.producers.KafkaProducers;
import com.example.kafkaspring.producers.KafkaStringProducers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/messages")
public class MessageController {

    @Autowired
    private KafkaStringProducers kafkaStringProducers;

    @Autowired
    private KafkaProducers kafkaProducers;

    @PostMapping
    public ResponseEntity<String> publish(@RequestBody MessageRequest messageRequest) {
        kafkaStringProducers.send("newTopicTest", messageRequest.getMessage());
        return new ResponseEntity<>(messageRequest.getMessage(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<User> publishUser(@RequestBody User user) {
        kafkaProducers.sendUser("reflectoring-user", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
