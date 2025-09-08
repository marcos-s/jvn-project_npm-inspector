# Multi-stage Dockerfile for NPM Package Inspector

# Stage 1: Build Frontend
FROM node:18-alpine AS frontend-builder

WORKDIR /app/frontend

# Copy package files
COPY src/frontend/package*.json ./

# Install dependencies
RUN npm ci --only=production

# Copy source code
COPY src/frontend/ ./

# Build the frontend
RUN npm run build

# Stage 2: Build Backend
FROM maven:3.9.6-eclipse-temurin-21 AS backend-builder

WORKDIR /app

# Copy Maven files
COPY pom.xml ./

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code (only Java files, not frontend)
COPY src/main/ ./src/main/
COPY src/test/ ./src/test/

# Build the application (skip frontend plugin)
RUN mvn clean package -DskipTests -Dfrontend.skip=true

# Stage 3: Production Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install curl
RUN apk add --no-cache curl

# Copy the built JAR
COPY --from=backend-builder /app/target/*.jar app.jar

# Copy the built frontend
COPY --from=frontend-builder /app/frontend/dist ./static

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/api/packages/react || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
