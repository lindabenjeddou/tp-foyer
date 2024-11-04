
FROM openjdk:11

ADD http://192.168.164.133:8081/repository/maven-releases/tn/esprit/tp-foyer/5.0.0/tp-foyer-5.0.0.jar /tp-foyer-5.0.0.jar

EXPOSE 8089

CMD ["java", "-jar", "/app/app.jar"]