import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerTransaction {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093,localhost:9094");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction-id-1");


        Producer<String, String> producer = new KafkaProducer<>(properties);
        producer.initTransactions();

        try {
            producer.beginTransaction();
            producer.send(new ProducerRecord<>("transaction-topic-1", "Hello"));
            producer.send(new ProducerRecord<>("transaction-topic-2", "How are you"));
            producer.commitTransaction();
        } catch (Exception ex) {
            producer.abortTransaction();
            producer.close();
            throw new RuntimeException(ex);
        }
        producer.close();
    }
}
