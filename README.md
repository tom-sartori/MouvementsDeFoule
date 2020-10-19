# MouvementsDeFoule

## Informations importantes 
- Réunion tous les **vendredis à 12h20** dans le bureau de M. Bougeret. 
- Soutenance 11-12 janvier. 
- 13 vendredis jusqu'au 08/01/2020
  - Ven 24/10 partiels. 
  - Ven 31/10 vacances. 
  - Ven 26/12 vacances. 
  - Ven 01/01 vacances. 
  - Ven 09/01 dernier vendredi. 
  -> 9 vendredis avec rdv. 

### Comptes rendus 
#### Réunion du 09/10/2020 
- Réunion tous les **vendredis à 12h20** dans le bureau de M. Bougeret. 
- Création du répertoire **GitHub**. 
- Regarder le **nombre de vendredis** avant la soutenance (semaines fériées/vacances...). 
- **Choisir le langage** de programmation pour le projet. 
- **Regarder la [vidéo](https://www.youtube.com/watch?v=mBDNykcauYc)** introduisant le sujet. 

- Idées des différentes versions du projet : <br>
  - V1 : 
     - Affichage de l'interface de base. 
     - Obstacles rectangles. 
     - Objet unique qui se déplace jusqu'à la sortie. 
     - Les objets sont des simples points. 
  - V2 : 
     - Gestion de plusieurs objets (sans collisions). 
  - V3 : 
     - Gestion des collisions sur plusieurs objets. 
     - Gestion du rayon de chaque objet. 
  - Suite : 
    - Possibilité d'évolution sur les comportements humains observés.
 - Très grande possibilité d'évolution dans les versions. <br>


#### Réunion du 16/10/2020 
- Avant la réunion, nous (sans les professeurs) avons convenu de faire une réunion chaque lundi midi. Un fichier indiquant les comptes-rendus de ces réunions sera bientot ajouté à la base du projet. 
- Avancement avant la réunion : 
  - Classes Salle, Personnage, Sortie, <<abstract>> Obstacle, ObstacleRectangle. 
  - Possibilité d'avoir une multitude de porsonnages et de sorties dans une salle. 
  - Les personnages se déplacent dans des salles et vont vers la sortie la plus proche. 
  - Pas de gestion du rayon du perso et pas de collisions. 
  - Classe Obstacle ajouté mais pas encore fonctionnelle. 
- Le langage sera javaFX. 
- Pour savoir si on touche un obstacle sur le chemin, il faut déterminer si deux segements s’intersèctent. 
  - Plusieurs manière de faire le calcul sont possibles (à chercher). 

- Objectif pour la semaine d'après : 
  - Annoter et clarifier le code déjà existant. 
  - Modifier la boucle principal pour qu'elle soit dans Salle et non pas une par personne. 
  - Annoter et clarifier le code déjà existant. 
  - Travailler sur la partie mathématique de l'algo de collision avec les obstacles. 
  - Implémenter cette fonctionnalité. 
  - Effectuer un maximum de tests unitaires. 
