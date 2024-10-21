# Stage 1: Build the application
FROM eclipse-temurin:21 AS build  
WORKDIR /app
COPY pom.xml .  
COPY src ./src  
RUN ["mvn", "package", "-DskipTests"] 

# Stage 2: Run the application
FROM amazoncorretto:21 
WORKDIR /app
COPY --from=build /app/target/inventory-service.jar ./inventory-service.jar 

# Define the command to run your app
ENTRYPOINT ["java", "-jar", "inventory-service.jar"]
