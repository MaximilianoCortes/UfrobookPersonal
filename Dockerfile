FROM maven:3.8.5-openjdk-17-slim AS builder

WORKDIR /build

COPY . .

# Establecer la variable de entorno DB_HOST
ENV DB_HOST=host.docker.internal:5432

RUN mvn clean package

FROM openjdk:17-slim

EXPOSE 3001

COPY --from=builder /build/target/ /app/


CMD ["java","-jar","/app/ufrobook-0.0.1-SNAPSHOT.jar"]
