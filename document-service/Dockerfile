FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /workspace/app
COPY . .
RUN ["mvn", "clean", "package", "-DskipTests"]

FROM amazoncorretto:21
COPY --from=builder /workspace/app/target/document-service-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
