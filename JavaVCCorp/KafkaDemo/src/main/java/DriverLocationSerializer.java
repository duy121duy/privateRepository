import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class DriverLocationSerializer implements Serializer<DriverLocation> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public byte[] serialize(final String topic, final DriverLocation data) {
        try {
            return OBJECT_MAPPER.writeValueAsString(data).getBytes();
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot serialize message to json");
        }
    }

}
