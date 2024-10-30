FROM openjdk:17-jdk-slim


EXPOSE 8089

# Variables pour Nexus (à adapter selon votre configuration)
ARG NEXUS_URL=http://http://192.168.158.138:8081
ARG REPO_PATH=repository/maven-releases/tn/esprit/tp-foyer/5.0.0/tp-foyer-5.0.0.jar
ARG NEXUS_USERNAME=admin
ARG NEXUS_PASSWORD=admin

# Récupérer le livrable depuis Nexus et le renommer
RUN apt-get update && apt-get install -y curl && \
    curl -o tp-foyer-5.0.0.jar -u $NEXUS_USERNAME:$NEXUS_PASSWORD $NEXUS_URL/$REPO_PATH

# Commande pour lancer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/tp-foyer-5.0.0.jar"]