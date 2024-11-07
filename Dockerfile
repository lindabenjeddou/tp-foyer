FROM openjdk:11

# Télécharge le fichier JAR et le renomme pour correspondre au nom dans CMD
ADD http://192.168.164.133:8081/repository/maven-releases/tn/esprit/tp-foyer/5.0.0/tp-foyer-5.0.0.jar /app/tp-foyer.jar

# Expose le port sur lequel l'application s'exécute
EXPOSE 8089

# Démarre l'application
CMD ["java", "-jar", "/app/tp-foyer.jar"]
