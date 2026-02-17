# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy all files to the container
COPY . /app

# Compile the Java server
RUN javac Server.java

# Run the server
CMD ["java", "Server"]
