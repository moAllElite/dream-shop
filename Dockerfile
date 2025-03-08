# Use the official Maven image with Eclipse Temurin 17 as the build stage
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files (pom.xml and .mvn directory)
COPY pom.xml .
COPY .mvn .mvn

# Copy the source code
COPY src src

# Build the application (skip tests for faster builds)
RUN mvn -Dspring.skipTest=true clean package

# Use a lightweight base image for the final stage
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage to the final image
COPY --from=build /app/target/*.jar app.jar

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]