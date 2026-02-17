# Use a prebuilt AdoptOpenJDK 17 image from GHCR
FROM ghcr.io/adoptium/temurin17:17.0.8_7-jdk

WORKDIR /app
COPY . /app

RUN javac Server.java

CMD ["java", "Server"]
