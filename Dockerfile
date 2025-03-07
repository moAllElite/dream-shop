##Use the latest ubuntu distribution
#FROM ubuntu:25.04
#LABEL authors="Mouhamed NIANG"
## Use an official Maven image as the base image
#FROM maven:3.9.9-openjdk:17-alpine AS build
## Set the working directory in the container
#WORKDIR /app
## Copy the pom.xml and the project files to the container
#COPY pom.xml .
#COPY src ./src
#
## Build the application using Maven
#RUN mvn clean package -DskipTests
#
##define arguments
#ARG APP_VERSION=1.0.0-SNAPSHOT
#
## Set the working directory in the container
#WORKDIR /app
## Copy the built JAR file from the previous stage to the container
#COPY --from=build /build/target/dream-shop-*.jar /app/
#
##Expose the port the spring boot application will run on
#EXPOSE 8080
#
##Command to run the application
#CMD  ["java","-jar","dream-shop.${APP_VERSION}.jar"]
