# Use OpenJDK 17 JDK slim image (specific version)
FROM openjdk:17.0.8-jdk-slim

# Set working directory
WORKDIR /app

# Copy all files to the container
COPY . /app

# Compile Java server
RUN javac Server.java

# Run the server
CMD ["java", "Server"]


# Set working directory
WORKDIR /app

# Copy all files to the container
COPY . /app

# Compile the Java server
RUN javac Server.java

# Run the server
CMD ["java", "Server"]
