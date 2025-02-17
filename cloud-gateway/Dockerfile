FROM amazoncorretto:17.0.12-alpine
LABEL authors="Katniss"
ARG JAR_FILE=cloud-gateway/target/*.jar
COPY ${JAR_FILE} app-cg.jar
COPY cloud-gateway/src/main/resources/application-docker.yml application.yml
ENTRYPOINT ["java","-Dspring.config.location=/application.yml","-jar","/app-cg.jar"]
