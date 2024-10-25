FROM openjdk:17-jdk-slim
EXPOSE 8080
ADD target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]