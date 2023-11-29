FROM maven:3.8-openjdk-8 AS build
RUN mkdir /uploads
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src src

RUN mvn clean package  -Dskiptests

FROM openjdk:8-jre-slim-stretch
RUN mkdir /uploads

COPY --from=build /target/demo1-0.0.1-SNAPSHOT.jar demo1.jar

EXPOSE 8080
ENTRYPOINT [ "java","-jar","demo1.jar" ]
