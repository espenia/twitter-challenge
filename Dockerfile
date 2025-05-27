# Use a base image with a JDK (Java Development Kit)
FROM openjdk:17-jdk-slim AS build

# Set the working directory in the container
WORKDIR /app

COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# Fix line ending issues with gradlew (convert CRLF to LF)
RUN apt-get update && apt-get install -y dos2unix && dos2unix ./gradlew && chmod +x ./gradlew
RUN ./gradlew build || return 0

# Copy the source code of your Spring Boot application into the container
COPY . /app

# Fix line endings again for the copied files
RUN find . -type f -name "*.sh" -o -name "gradlew" | xargs dos2unix
RUN chmod +x ./gradlew

# Build the Spring Boot application inside the container
RUN ./gradlew build -x test
RUN ./gradlew test

# Build the Spring Boot application inside the container
RUN ./gradlew bootJar -x test

# Verify the JAR file was created and find its location
RUN find /app/build -name "*.jar" | sort

# Use a base image with a JRE (Java Runtime Environment)
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the compiled Spring Boot JAR file into the container from the build stage
COPY --from=build /app/build/libs/twitter-challenge-espenia-0.0.1-SNAPSHOT.jar /app/application.jar

# Expose the port your Spring Boot application is running on (default is 8080)
EXPOSE 8080

# Command to run your Spring Boot application
CMD ["java", "-jar", "/app/application.jar"]
