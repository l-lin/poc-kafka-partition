package lin.louis.poc.kafka.partition;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.CommandLineRunner;

import java.time.Duration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MessageConsumer implements CommandLineRunner {

    private static final long PERIOD = Duration.ofMillis(500).toMillis();

    private static final Duration TIMEOUT = Duration.ofSeconds(1);

    private final KafkaConsumer<String, String> kafkaConsumer;

    private final String topic;

    @Override
    public void run(final String... args) {
        kafkaConsumer.subscribe(List.of(topic));

        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(PERIOD);
                kafkaConsumer.poll(TIMEOUT)
                        .forEach(consumerRecord -> LOGGER.info(
                                "[key:{}][value:{}]",
                                consumerRecord.key(),
                                consumerRecord.value()
                        ));
            }
        } catch (Exception e) {
            LOGGER.error("Kafka consumer error", e);
        } finally {
            kafkaConsumer.close();
        }
    }
}
