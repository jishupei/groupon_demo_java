FROM openjdk:11-jdk-slim AS build
WORKDIR /app

COPY ./target/groupon-demo-java-0.0.1-SNAPSHOT.jar /app

EXPOSE 80

ENTRYPOINT ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]