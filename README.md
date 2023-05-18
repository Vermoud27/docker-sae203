**Nom    :** Marouard Louis, Cocagne Oscar, Ravenel Martin, Lelievre David
**Groupe :** Groupe 16
**Année  :** 2023

# docker-sae203

## Utilisation de l'application

1. Construire l'image avec Dockerfile 

       docker build -t <nom image> ./docker-server/

2. Créer un conteneur docker avec l'image créée plus tôt

       docker run -p <port>:8080 <nom image>
       
3. Pour accéder a l'application, Ouvrir un deuxième Terminal puis tapper les commandes :
	
       cd src
	
       java Client2

