FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar
COPY config config
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]