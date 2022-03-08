package com.example.kafkaspring.listeners;

import com.example.kafkaspring.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class KafkaListeners {
    private final Logger LOG = LoggerFactory.getLogger(KafkaListeners.class);

    @KafkaListener(topics = "newTopicTest", groupId = "groupId", containerFactory = "stringKafkaListenerContainerFactory")
    @SendTo("newTopicSendTo")
    public String listener(String data) {
        System.out.println("Listener received: " + data);
        return data + " receive from send to";
    }

    @KafkaListener(topics = "newTopicSendTo", groupId = "groupIdSendTo", containerFactory = "stringKafkaListenerContainerFactory")
    public void listenerSendTo(String data) {
        System.out.println("Listener received: " + data);
    }

    @KafkaListener(groupId = "reflectoring-user", topics = "reflectoring-user", containerFactory = "userKafkaListenerContainerFactory")
    public void listenerUser(User user) throws JsonProcessingException {
        LOG.info("Json message received using Kafka listener " + user);
        System.out.println(user);
    }


//    @KafkaListener(groupId = "reflectoring-group-3",
//            topicPartitions = @TopicPartition(
//                    topic = "newTopicTest",
//                    partitionOffsets = {@PartitionOffset(
//                            partition = "0",
//                            initialOffset = "0")}))
//    void listenToPartitionWithOffset(
//            @Payload String message,
//            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
//            @Header(KafkaHeaders.OFFSET) int offset) {
//        LOG.info("Received message [{}] from partition-{} with offset-{}",
//                message,
//                partition,
//                offset);
//    }
}
