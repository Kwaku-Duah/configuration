# Use OpenJDK as the base image
FROM openjdk:21-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file into the container
COPY target/*.jar app.jar  

# Expose the port that your application runs on
EXPOSE 8084

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
