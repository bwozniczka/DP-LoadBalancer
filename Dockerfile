FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./target/LoadBalancer-1.0-SNAPSHOT.jar /app/LoadBalancer.jar

CMD ["java", "-jar", "LoadBalancer.jar"]
