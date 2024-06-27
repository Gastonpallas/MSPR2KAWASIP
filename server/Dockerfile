# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests

# Stage 2: Build the runtime image
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/*.jar /app/kawasip.jar
CMD ["java", "-jar", "kawasip.jar"]
