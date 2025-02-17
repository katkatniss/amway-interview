FROM amazoncorretto:17.0.12-alpine
LABEL authors="Katniss"
ARG JAR_FILE=service-registry/target/*.jar
COPY ${JAR_FILE} app-sr.jar
COPY service-registry/src/main/resources/application.yml application.yml
ENTRYPOINT ["java","-Dspring.config.location=/application.yml","-jar","/app-sr.jar"]