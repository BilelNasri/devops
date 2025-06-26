# Use a lightweight JDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR built by Maven
COPY target/adoption-Project-0.0.1-RELEASE.jar app.jar

# Expose the app port
EXPOSE 8089

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
