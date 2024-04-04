FROM openjdk:21-slim

WORKDIR /app

CMD ["./gradlew", "clean", "build", "bootJar"]
COPY build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
