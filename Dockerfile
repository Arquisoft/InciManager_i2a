FROM maven:3.5-jdk-8-alpine
WORKDIR /usr/src/inciManageri2a
COPY . /usr/src/inciManageri2a/
RUN mvn package -Dmaven.test.skip=true
EXPOSE 8081
CMD ["java", "-jar", "jars/inciManager_i2a-0.0.1.jar", "--spring.kafka.bootstrap-servers=kafka:9092", "--url.agentsmodule=http://cf-azuc-docker-node-0037.az.codefresh.io:32989", "--spring.data.mongodb.host=mongo"]