FROM gradle:8.7-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

FROM openjdk:17
WORKDIR /
COPY --from=builder /app/build/libs/richstone-cargo-0.0.1-SNAPSHOT.jar /
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "richstone-cargo-0.0.1-SNAPSHOT.jar"]