# Use an official Gradle image as the base
FROM gradle:7.6-jdk17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy Gradle wrapper files (if used)
COPY gradlew .
COPY gradle gradle

# Copy the entire project into the container
COPY . .