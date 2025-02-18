FROM amazoncorretto:17.0.12-alpine
LABEL authors="Katniss"
ARG JAR_FILE=prize-wheel-service/target/*.jar
COPY ${JAR_FILE} app.jar
COPY prize-wheel-service/src/main/resources/application-docker.yml application.yml
ENTRYPOINT ["java","-Dspring.config.location=/application.yml","-jar","/app.jar"]
