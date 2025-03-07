#1. Build stage
#Use the latest ubuntu distribution
#FROM : Définit l’image de base sur laquelle l’image sera construite.
FROM ubuntu:25.04
LABEL authors="Mouhamed NIANG"
# Use an official Maven image as the base image
FROM maven:3.9.9-openjdk:17 AS build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
#COPY : Copie les fichiers depuis votre machine locale vers une nouvelle couche de l’image.
COPY pom.xml .
#install dependencies
RUN mvn dependency:go-offline
COPY src ./src
#build the app without
RUN mvn clean package -DskipTests
#define arguments
ARG APP_VERSION=1.0.0
#2. Runtime stage
FROM openjdk:17-alpine
# Set the working directory in the container
WORKDIR /app
# Copy the built JAR file from the previous stage to the container
COPY --from=build /build/target/dream-shop-*.jar /app/

#Expose the port the spring boot application will run on
EXPOSE 8080

ENV DB_URL=jdbc:mysql://container_mysql-dreamshop-db:3306/dreamshop-db
ENV JAR_VERSION=${APP_VERSION}
#Command to run the application
#CMD : Spécifie la commande qui sera exécutée lorsque le conteneur démarre.
CMD  java -jar -Dspring.datasource.url=${DB_URL} dream-shop.${JAR_VERSION}.jar
#CMD  java -jar  dream-shop.${JAR_VERSION}.jar