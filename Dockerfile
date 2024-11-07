FROM openjdk:17-jdk-alpine
EXPOSE 8089
ADD target/tp-foyer-5.0.0.jar /kadden.jar
ENTRYPOINT ["java", "-jar", "/kadden.jar"]
