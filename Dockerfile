
#Use the latest ubuntu distribution
#FROM : Définit l’image de base sur laquelle l’image sera construite.
FROM openjdk:17-jdk-alpine AS build
# Copy the built JAR file from the previous stage to the container
COPY pom.xml mvnw ./
COPY .mvn .mvn
#RUN ./mvnw dependency:off-

COPY src src
RUN mvn -Dspring.skipTest=true  package

FROM openjdk:17-jdk-alpine
WORKDIR app
COPY --from=build target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]