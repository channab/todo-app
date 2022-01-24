FROM openjdk:8-jdk-alpine
MAINTAINER baeldung.com
COPY target/to-do-app-1.0.0.jar to-do-app.jar
ENTRYPOINT ["java","-jar","/to-do-app.jar"]