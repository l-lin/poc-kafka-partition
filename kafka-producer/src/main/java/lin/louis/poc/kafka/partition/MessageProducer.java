package lin.louis.poc.kafka.partition;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.CommandLineRunner;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
public class MessageProducer implements CommandLineRunner {

    private static final Random RANDOM = new SecureRandom();

    private static final List<String> IDS = IntStream.range(0, 5)
            .mapToObj(Integer::toString)
            .collect(Collectors.toList());

    private final KafkaProducer<String, String> kafkaProducer;

    private final AtomicInteger index = new AtomicInteger();

    private final String topic;

    private final Duration interval;

    @Override
    public void run(final String... args) {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(interval.toMillis());
                sendMessage();
            }
        } catch (Exception e) {
            LOGGER.error("Kafka producer error", e);
        } finally {
            kafkaProducer.close();
        }
    }

    private void sendMessage() throws ExecutionException, InterruptedException {
        int idIndex = RANDOM.nextInt(IDS.size());
        String id = IDS.get(idIndex);
        LOGGER.info("Sending message {}", id);
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>(topic, id, Integer.toString(index.getAndIncrement()));
        kafkaProducer.send(producerRecord).get();
    }
}
