package InvoceExample;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;
import java.util.stream.IntStream;

public class InvoiceProducer {
    public static void main(String[] args) {
        Random random = new Random();
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, String.valueOf(2048 * 0));

        Producer<String, Invoice> producer = new KafkaProducer<>(props);
        IntStream.range(0, 1000)
                .parallel()
                .forEach(i -> {
                    Invoice invoice = Invoice.builder()
                            .invoiceNumber(String.format("%05d", i))
                            .storeId(i % 5 + "")
                            .created(System.currentTimeMillis())
                            .valid(random.nextBoolean())
                            .build();
                    producer.send(new ProducerRecord<>("invoice-topic", invoice));
                });
        producer.flush();
        producer.close();
    }
}