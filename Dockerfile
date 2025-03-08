#1. Build stage
#Use the latest ubuntu distribution
#FROM : Définit l’image de base sur laquelle l’image sera construite.
FROM ubuntu:latest
LABEL authors="Mouhamed NIANG"
# Use an official Maven image as the base image
FROM maven:3.9.9-eclipse-temurin-17 AS build
# Set the working directory in the container
WORKDIR /app


#define arguments
ARG APP_VERSION=1.0.0
#2. Runtime stage
FROM openjdk:17
# Set the working directory in the container
WORKDIR /app
# Copy the built JAR file from the previous stage to the container
COPY  /build/target/dream-shop-*.jar app.jar
#COPY target/*.jar app.jar

#Expose the port the spring boot application will run on
EXPOSE 8080

ENV DB_URL=jdbc:mysql://container_mysql-dreamshop-db:3306/dreamshop-db
ENV JAR_VERSION=${APP_VERSION}
#Command to run the application
#CMD : Spécifie la commande qui sera exécutée lorsque le conteneur démarre.
#CMD  java -jar -Dspring.datasource.url=${DB_URL} dream-shop.${JAR_VERSION}.jar
CMD  java -jar  dream-shop.${JAR_VERSION}.jar
ENTRYPOINT ["java", "-jar", "dream-shop-1.0.0.jar"]