FROM openjdk:8-jdk-alpine
EXPOSE 8089
ADD target/tp-foyer-5.0.0.jar kadden.jar
ENTRYPOINT ["java", "-Xmx256m", "-Xms256m", "-jar", "/kadden.jar"]
