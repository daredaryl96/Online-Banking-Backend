# Dockerfile.dev

# Use an official OpenJDK image
FROM eclipse-temurin:23-jdk

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory
WORKDIR /app

# Copy only the POM and install dependencies first (optional optimization)
COPY pom.xml .

# Download Maven dependencies
RUN mvn dependency:go-offline

# By default, Dockerfile will not copy code. We will mount code as volume in docker-compose.

# Start Spring Boot using spring-boot:run with DevTools support
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.fork=false"]
