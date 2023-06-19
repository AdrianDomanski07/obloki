FROM openjdk:11
# Ustawienie autorstwa
LABEL author="Adrian Domanski"
VOLUME /tmp
# Nasluchiwanie kontenera na porcie 8080
EXPOSE 8080
# Monitorowanie zdrowia kontenera
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/health || exit 1
# Sciezka do pliku JAR aplikacji
ARG JAR_FILE=target/obloki-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
# Uruchomienie aplikacji
ENTRYPOINT ["java","-jar","/app.jar"]