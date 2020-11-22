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

<br/>

### Comptes rendus <br/>
#### Réunion du 09/10/2020 
- Réunion tous les **vendredis à 12h20** dans le bureau de M. Bougeret. 
- Création du répertoire **GitHub**. 
- Regarder le **nombre de vendredis** avant la soutenance (semaines fériées/vacances...). 
- **Choisir le langage** de programmation pour le projet. 
- **Regarder la [vidéo](https://www.youtube.com/watch?v=mBDNykcauYc)** introduisant le sujet. 

- Idées des différentes versions du projet : 
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
 - Très grande possibilité d'évolution dans les versions. 

<br/> 


#### Réunion du 16/10/2020 
- Avant la réunion, nous (sans les professeurs) avons convenu de faire une réunion chaque lundi midi. Un fichier indiquant les comptes-rendus de ces réunions sera bientot ajouté à la base du projet. 
- Avancement avant la réunion : 
  - Classes Salle, Personnage, Sortie, <<abstract>> Obstacle, ObstacleRectangle. 
  - Possibilité d'avoir une multitude de porsonnages et de sorties dans une salle. 
  - Les personnages se déplacent dans des salles et vont vers la sortie la plus proche. 
  - Pas de gestion du rayon du perso et pas de collisions. 
  - Classe Obstacle ajouté mais pas encore fonctionnelle. 
<br/>
  
- Le langage sera javaFX. 
- Pour savoir si on touche un obstacle sur le chemin, il faut déterminer si deux segements s’intersèctent. 
  - Plusieurs manière de faire le calcul sont possibles (à chercher). 
  <br/>

- Objectif pour la semaine d'après : 
  - Annoter et clarifier le code déjà existant. 
  - Modifier la boucle principal pour qu'elle soit dans Salle et non pas une par personne. 
  - Travailler sur la partie mathématique de l'algo de collision avec les obstacles. 
  - Implémenter cette fonctionnalité. 
  - Effectuer un maximum de tests unitaires. 

<br/>



#### Réunion du 23/10/2020 
- Avant la réunion : 
  - Nous avons annoté et essayé de clarifier une partie du code même si ce dernier reste encore assez peu explicite pour le moment. 
  - La boucle principale est maintenant dans la classe Salle et permet donc de déplacer tous les personnages. Les persos sont contenus dans une liste dans la classe Salle. Lorsque les persos sortent du cadre de la salle, ils sortent de la liste et lorsqu'il n'y a plus de persos dans la salle, la boucle s'arrête (pas de claculs inutiles). 
  - La partie mathématique des collisions avec les obstacles (intersection ou non de deux segments), nous a été expliquée par M. Marie Jeanne. Nous avons bien compris comment nous devons procéder et avons effectué les calculs/tests des différents cas possibles sur papier. L’algorithme correspondant ainsi que des tests ont commencé a être implémentés mais ne sont pas encore terminés à ce jour. 
  - En accord avec notre cours d'ACDA, nous avons décidé de diviser complètement la partie affichage des objets de la partie calculs. A ce jour, les deux se mélangeaient dans notre code et c'était peu clair. Ce point n'est pas encore effectif sur notre code. 
  <br/> 
  
- Pendant la réunion : 
  - Nous avons exposé les différentes fonctionnalités apportées au projet pendant la semaine. 
  - Il nous était assez difficile de concevoir la manière dont le perso allait pouvoir précalculer son itinéraire. De ce fait, il nous a été indiqué qu'il fallait voir le problème sous forme d'un graphe composé des points des obstacles et sorties et par la suite, effectuer un calcul du plus court chemin vers la sortie. 
  - Pour le plus court chemin, l’algorithme de Dijkstra est une possibilité. 
  - Au niveau des sorties, il est possible d'ajouter une sortie unique fictive à distance zero de toutes les vraies sorties. 
  - Il faudra donc produire un algorithme permettant de trouver le plus court chemin vers la sortie pour chaque point du graphe. Ceci sera calculé indépendament des personnages. Après, chaque personne devra calculer quel point direct est le plus proche de la sortie, en additionant sa distance avec ce point. 
  - Il nous a été indiqué qu'il pouvait être difficile d'effectuer des tests unitaires pour ce problème mais que faire un affichage du graphe pouvait être pertinant. 
  - Nous avons également convenu qu'une classe Maths pouvait être pertinante afin de généraliser certains calculs. 
  - Pour une version future, il a été évoqué qu'il serait potentiellement pertinant d'ajouter une fonctionnalité permetant d'entrer dans le programme une image SVG d'un plan réel, afin d'effectuer des tests de mouvements de foule deçu. 
<br/> 

- Objectif pour la prochaine séance (séance du 06/11 car vacances) : 
  - Continuer de clarifier et d’annoter le code. 
  - Implémenter et tester la fonctionnalité d'intersection entre deux segments. 
  - Concevoir, implémenter et tester la partie sur les graphes et plus courts chemins. 
  - Lier les plus courts chemins avec les personnages afin d'avoir une detection des obstacles fonctionnelle. 
  - Séparer la partie graphique de la partie physique. 

<br/> 

#### Réunion du 06/11/2020 
- Nous avons discuté du code fait depuis le début du projet avec les professeurs. Cela leur a permis de mieux comprendre notre code et de nous faire des retours par rapport à leur compréhension.
- Modifications à apporter:
  - Enlever les répétitions qui se trouvent dans les classes ObstacleRectangles, Sortie et Point. 
  - Déplacer une Personne par rapport à son objectif et sa vitesse. (Pour ne plus qu'elle dépasse son objectif quand la vitesse est trop grande.)
  - Simplifier la classe Sortie en enlevant les attributs mur, longueur, distance et garder juste listePoint. 
  - Pour Point, faire une sous-classe qui va permettre de séparer les simples Point (un x et un y), des Point plus compliqués (qui contient un Point précédent, suivant). 
  - Salle ; essayer de séparer la fonction Handle de la fonction démarrer pour simplifier au maximum la classe salle.
  <br/>
  
  #### Réunion du 09/11/2020
  - Cette réunion s'est déroulée un lundi car nous voulions continuer de présenter le code, comme dans la réunions précédente. 
  - Nous avons vu les classes suivantes : 
    - MathCalcule
      - Manque de la vérification, de la prise en compte, du cas où une personne est sur le coin d'un obstacle et que son objectif est dans l'obstacle. Ce cas ne devrait pas arriver, mais il est necessaire de prendre en compte ce cas pour le bon fonctionnement du programme. 
      - Il faut annoncer que nous avons fait le choix que lorsque deux segments sont supperposés en un seul point, alors on dit qu'ils ne sont pas supperposés. 
      - Simplification de certaines fonctions. Enlever les répétitions. 
    - Graphe 
      - Eviter les répétitions dans les attributs. 
      - Il est necessaire de prendre en compte tous les points d'une sortie et pas uniquement les deux extrémités. 
      - Possibilité d'utiliser l’algorithme de Bellmann. 
      - M. Bougeret à proposé un pseudo code montrant une manière de résoudre nos chemins dans les graphes. 
    - Autres classes 
      - Vue rapide de certaines répétitions 
<br/>

#### Réunion du 13/11/2020
- Nous avons présenté la classe Personne. 
  - Simplification des attributs. 
  - Simplification de certaines fonctions basiques comme findDxDy. 
  - Le déplacement des personnes avec leurs rayons sera prit en compte par l'agrandissement des obstacles et non pas dans la classe Personne. 
  - Explication de la V1 des collisions entre personnes. 
<br/>

#### Réunion du 20/11/2020
- Nous avons principalement parlé au sujet des collisions. 
  - Explication de la V2. 
  - Bon fonctionnement. 
  - Difficultés sur l'explication de la "distanciation physique". 
  - V3 pour la semaine prochaine. 
- Conseils pour al gestion de collisions : 
  - Si les persos vont entre en collisions, alors un des deux ne bouge pas du tout. 
  - Ou sinon, il est possible d'un faire avancer un, d'une distance inférieure à la normale, afin qu'il colle celui devant lui. 
- Pour les obstacles, on partira du principe qu'on les diminuent graphiquement et non pas, qu'on les augmentent. 
- Il faudra faire plus de specification et de schémas pour expliquer notre code. 

