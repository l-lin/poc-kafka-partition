# Kafka partition POC

> POC playing with Kafka partitions

```bash
# build applications
mvn clean package
# first spawn kafka
docker-compose up -d kafka
# create topic records
docker exec -it kafka /usr/bin/kafka-topics --bootstrap-server kafka:29092 --create --partitions 2 --replication-factor 1 --topic records
# start producer & 2 consumers
docker-compose up -d --scale consumer=2
# checks logs
docker-compose logs -f consumer
# notice a key is not consumed by both consumers
# play by stopping one of the container and notice the other container takes all the messages
docker stop poc-kafka-partition_consumer_2
# when restarting, notice how kafka re-balance the messages
docker start poc-kafka-partition_consumer_2
```
