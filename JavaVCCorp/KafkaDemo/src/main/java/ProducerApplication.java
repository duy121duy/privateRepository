import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerApplication {

    public static void main(String[] args) {
        //Creating Properties
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DriverLocationSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        //Creating producers
        Producer<String, DriverLocation> producer = new KafkaProducer<>(properties);
        //prepare the record
        DriverLocation recordValue = new DriverLocation(1, 2, 3);
        ProducerRecord<String, DriverLocation> record = new ProducerRecord<>("transaction-topic-1", recordValue);
        //Sending message to Kafka Broker
        producer.send(record);
        producer.flush();
        producer.close();
    }
}
