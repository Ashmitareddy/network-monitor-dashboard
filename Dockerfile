# Stage 1: Build - Uses Maven and JDK 17 to compile your code
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
# This creates the .jar file in the target/ folder
RUN mvn clean package -DskipTests

# Stage 2: Runtime - A smaller image just to run the app
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copies the jar from the build stage to the runtime stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]