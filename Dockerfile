# Stage 1: build JAR
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn -pl restaurant-container -am clean package -DskipTests

# Stage 2: run JAR
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/restaurant-container/target/*.jar app.jar
EXPOSE 8484
ENTRYPOINT ["java", "-jar", "app.jar"]
