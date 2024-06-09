FROM maven:3.8.4-openjdk-17 as builder
ARG WAR_FILE=personal_finance_accounting-0.0.1-SNAPSHOT.war
COPY ${WAR_FILE} app.war
ENTRYPOINT ["java", "-jar", "app.war"]
