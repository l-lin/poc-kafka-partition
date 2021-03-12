package lin.louis.poc.kafka.partition;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@Data
@ConfigurationProperties(prefix = "kafka-producer")
public class KafkaProducerProperties {
    private String bootstrapServers;

    private String topic;

    private Duration interval;
}
