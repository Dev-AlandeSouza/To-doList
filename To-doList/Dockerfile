
FROM maven:3.8.4-openjdk-17 AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:17-jdk-slim

EXPOSE 8080

<<<<<<< HEAD
COPY --from=build /target/*.jar app.jar
=======
COPY --from=build /target/To-doList-1.0.0.jar app.jar
>>>>>>> 5d992af3590e3ae9fbb0962b7d4e47556043b55d

ENTRYPOINT ["java", "-jar", "app.jar"]
