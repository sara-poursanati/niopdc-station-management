FROM openjdk:21-jdk

WORKDIR /app

COPY target/fuelStation-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "/app/app.jar"]