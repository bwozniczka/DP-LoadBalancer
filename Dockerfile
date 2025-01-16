FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

# Copy the project files to the container
COPY . .

# Build the project
RUN mvn clean package

# Use a smaller base image for the runtime
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/LoadBalancer-1.0-SNAPSHOT.jar /app/LoadBalancer.jar

# Run the application
CMD ["java", "-jar", "LoadBalancer.jar"]