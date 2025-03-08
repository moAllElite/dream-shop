
#Use the latest ubuntu distribution
#FROM : Définit l’image de base sur laquelle l’image sera construite.
FROM maven:3.9.9-eclipse-temurin-17 AS build
# Copy the built JAR file from the previous stage to the container
COPY pom.xml mvnw ./
COPY .mvn .mvn

COPY src src
RUN ./mvnw -Dspring.skipTest=true clean package

FROM openjdk:17-jdk-alpine
WORKDIR app
COPY --from=build target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]