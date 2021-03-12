# Kafka partition POC

> POC playing with Kafka partitions

```bash
# build applications
mvn clean package
# first spawn kafka (with kowl, the kafka UI)
docker-compose up -d kowl
# create topic records
docker exec -it kafka /usr/bin/kafka-topics --bootstrap-server kafka:29092 --create --partitions 2 --replication-factor 1 --topic records
# start producer
docker-compose up -d producer
# start 2 consumers
docker-compose up -d consumer --scale consumer=2
# checks logs
docker-compose logs -f consumer
# notice an id is not consumed by both consumers
```
