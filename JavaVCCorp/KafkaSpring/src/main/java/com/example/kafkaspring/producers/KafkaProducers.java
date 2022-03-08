package com.example.kafkaspring.producers;

import com.example.kafkaspring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducers {
//    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaProducers.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaProducers(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUser(String topic, User user) {
        kafkaTemplate.send(topic, user);
    }

//    public void sendMessageWithCallback(String topic, String message) {
//        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, message);
//
//        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                LOGGER.info("Message [{}] delivered with partition {}", message, result.getRecordMetadata().partition());
//            }
//
//            @Override
//            public void onFailure(Throwable ex) {
//                LOGGER.warn("Unable to deliver message [{}]. {}", message, ex.getMessage());
//            }
//        });
//    }
}
