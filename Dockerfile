FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY build/libs/customers-0.0.1-SNAPSHOT.jar /app/customers.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/customers.jar"]