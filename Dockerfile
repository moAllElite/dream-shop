#Use the latest ubuntu distribution
FROM ubuntu:latest
LABEL authors="Mouhamed NIANG"
#Use the Open JDK 17 image from Docker Hub
FROM openjdk:17-jdk-alpine
# define jars file
ARG JAR_FILE=target/*.jar
#Copy the JAR file into the container
COPY ${JAR_FILE} app.jar
#Expose the port the spring boot application will run on
EXPOSE 8080
#Command to run the application
CMD  ["java","-jar","/app.jar"]
