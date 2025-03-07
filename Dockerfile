#Use the latest ubuntu distribution
#FROM : Définit l’image de base sur laquelle l’image sera construite.
FROM ubuntu:25.04
LABEL authors="Mouhamed NIANG"
# Use an official Maven image as the base image
FROM maven:3.9.9-openjdk:17-alpine AS build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
#COPY : Copie les fichiers depuis votre machine locale vers une nouvelle couche de l’image.
COPY pom.xml .
COPY src ./src

# Build the application using Maven
#RUN : Exécute une commande et crée une nouvelle couche avec le résultat.
RUN mvn clean package -DskipTests

#define arguments
ARG APP_VERSION=1.0.0-SNAPSHOT

# Set the working directory in the container
WORKDIR /app
# Copy the built JAR file from the previous stage to the container
COPY --from=build /build/target/dream-shop-*.jar /app/

#Expose the port the spring boot application will run on
EXPOSE 8080

#Command to run the application
#CMD : Spécifie la commande qui sera exécutée lorsque le conteneur démarre.
CMD  ["java","-jar","dream-shop.${APP_VERSION}.jar"]
