
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app
COPY . /app
RUN mvn clean install

FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=build /app/target/To-doList-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
