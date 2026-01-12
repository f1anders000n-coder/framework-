# Build stage
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copy maven files
COPY pom.xml .
COPY core/pom.xml core/
COPY persistence/pom.xml persistence/
COPY web/pom.xml web/

# Copy source code
COPY core/src core/src
COPY persistence/src persistence/src
COPY web/src web/src

# Install Maven and build
RUN apk add --no-cache maven && \
    mvn clean package -pl web -am -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/web/target/web-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
