
#Use the latest ubuntu distribution
#FROM : Définit l’image de base sur laquelle l’image sera construite.


# Set the working directory in the container

# Set the working directory in the container

# Copy the built JAR file from the previous stage to the container
FROM openjdk:17-jdk-alpine
LABEL authors="Mouhamed NIANG"
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#Command to run the application
