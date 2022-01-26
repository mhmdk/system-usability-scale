# syntax=docker/dockerfile:1
FROM maven:3.8.4-jdk-11 AS build
WORKDIR sus-build
COPY pom.xml ./
COPY src/ ./src/
RUN mvn clean package

FROM openjdk:11
COPY --from=build sus-build/target/sus-standalone-1.jar /app/
EXPOSE 8080
CMD ["java","-jar","/app/sus-standalone-1.jar"]
