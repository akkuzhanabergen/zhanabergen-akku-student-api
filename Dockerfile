# Build stage: use a JDK to compile the Spring Boot project.
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /workspace
COPY gradlew build.gradle settings.gradle ./
COPY gradle/ ./gradle/
COPY src/ ./src/
RUN chmod +x ./gradlew && ./gradlew --no-daemon clean bootJar

# Runtime stage: only a JRE and the generated JAR are included.
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd --system --uid 10001 --create-home appuser
COPY --from=builder /workspace/build/libs/*.jar /app/student-api.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/student-api.jar"]
