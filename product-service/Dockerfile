# Stage 1: Build the application
FROM eclipse-temurin:21 AS build  
WORKDIR /app
COPY pom.xml .  
COPY src ./src  
RUN ["mvn", "package", "-DskipTests"] 

# Stage 2: Run the application
FROM amazoncorretto:21 
WORKDIR /app
COPY --from=build /app/target/product-service.jar ./product-service.jar 

# Define the command to run your app
ENTRYPOINT ["java", "-jar", "product-service.jar"]
