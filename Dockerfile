# Stage 1: Build - Using Eclipse Temurin JDK 17
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
# This creates the .jar file in the target/ folder
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime - Using a smaller JRE image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copies the jar from the build stage to the runtime stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]