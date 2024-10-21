# Use a base image with JDK
FROM eclipse-temurin:21 AS build  
WORKDIR /app

# Copy the Maven or Gradle build files and source code
COPY pom.xml . 
COPY src ./src  

# Build the application (skipping tests)
RUN ["mvn", "package", "-DskipTests"]

# Stage 2: Create a lightweight image for running the application
FROM amazoncorretto:21  
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/discovery.jar ./discovery.jar 
# Expose the port the app runs on
EXPOSE 8761  

# Define the command to run your app
ENTRYPOINT ["java", "-jar", "discovery.jar"]
