FROM openjdk:17-jdk-alpine
WORKDIR /app
RUN apk add --no-cache \
    freetype \
    fontconfig \
    ttf-dejavu
COPY target/financetracker.jar /app/financetracker.jar
ENTRYPOINT ["java", "-jar", "/app/financetracker.jar"]
