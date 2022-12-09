FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17-jdk-slim
MAINTAINER blue.farid
COPY --from=build /home/app/target/box-demo-1.0.0.jar box-demo-1.0.0.jar
ENTRYPOINT ["java","-jar","/box-demo-1.0.0.jar"]
