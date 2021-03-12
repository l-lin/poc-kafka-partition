package lin.louis.poc.kafka.partition;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@ConfigurationProperties(prefix = "kafka-consumer")
public class KafkaConsumerProperties {
    private String bootstrapServers;

    private String topic;
}
