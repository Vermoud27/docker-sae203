# Utiliser l'image debian officielle comme image parent
FROM debian:latest

#Instalation de java 
RUN  apt-get update && apt-get install -y default-jdk

WORKDIR /app

COPY ./Server/Server.java /app/
COPY ./Client/Client2.java /app/

RUN javac /app/Server.java
RUN javac /app/Client2.java

# Rendre le port 80 accessible au monde en dehors de ce conteneur
EXPOSE 8080 

CMD ["java", "-classpath", "/app" , "Server"]


