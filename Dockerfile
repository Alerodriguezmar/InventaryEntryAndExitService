FROM openjdk:17-jdk-slim
RUN mkdir /app
COPY src/main/resources/InventaryEntryAndExitService-1.0.jar /app
EXPOSE 22100
CMD ["java", "-jar", "/app/InventaryEntryAndExitService-1.0.jar"]