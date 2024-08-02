# Use the official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/user-mongo-1.0.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]