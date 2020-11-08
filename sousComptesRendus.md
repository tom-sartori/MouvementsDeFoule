# Sous comptes rendus

<br/>
Dans le cadre de ce projet, nous avons décidé de faire une réunion chaque lundi midi (sans les professeurs) en plus de la réunion habituelle du vendredi. 
Il est évidemment possible que nous fassions d'autres réunions ponctuelles dans la semaine. 

Ce fichier indiquera donc les nouveautés et idées apportées au projet au fur et à mesure. 
<br/>

### Semaine du 12/10 
Idées : 
- Créer des branches GitHub. 
- Organiser le projet à la manière d'une méthode agile simplifiée. 
  - Se rapproche d'une To Do list, mais possiblement avec d'autres colonnes et des attribution aux personnes par stories. 
  - Création des premières issues très générales le week-end. 
- Chaine youtube <a href="https://www.youtube.com/c/Fouloscopie/videos" target="_blank">Fouloscopie</a> à check. Plus ou moins en rapport avec notre projet. 
- Possibilité de faire évoluer en un jeu. 
  - Plusieurs niveaux avec des persos ou objets imposés et l'objectif serait de sortir de la salle en un temps imparti. 

<br/>

### Semaine du 19/10 
- Gestion de la boucle principale mise dans Salle. 
  - Les personnages sortent de la liste lorsqu'ils sont à l'arrive. Donc plus de problème de calcul de coordonnées après la sortie. 
  - La boucle s’arrête lorsque tous les personnages sont sortis. 
- Début d'un diagramme de classes 
- Début du travail sur la partie mathématique de collision avec les obstacles. 
  - Après en avoir parlé avec notre professeur de modélisation mathématique, l'idée d'utiliser un système matriciel nous a paru plus simple que l'utilisation des vecteurs.
- Mise en commun sur git, sur la branche main. Quelques difficultés au début, mais c'est maintenant bien comprit. (21/10)
- La mise en place d'un tableau de sprint a été fait, une note est donnée à ces issues par rapport à leur importance. (21/10)
- Après la mise en commun, il a été convenu de reprendre le code existant afin de séparer la partie physique et la partie graphique du code. Il faudra donc créer des classes ControllerNom et des classes Nom. Les classes Nom permettrons de faire tous les calculs et les classes ControllerNom, afficherons les objets. Conception encore à valider avec les enseignants. (21/10)
- La code de la fonction indiquant le croisement ou non de deux segments a été implémenté et testé. La fonction a été adaptée avec un obstacle en argument. (22/10) 
<br/>

**Question**
- Faudra-t-il faire une mise à l'échelle avec une salle déjà existante ?
- Faut-il qu'on sépare la partie graphique de la partie physique ?
- Comment fonctionne le pull request ?
- Une classe Maths pour faire des calculs génériques est-elle pertinante et necessaire ? 

<br/>

### Semaine du 26/10
- Gestion du Graphe.
  - Creation des class **Chemin** et **Graphe**.
  - Les contrôleurs correspondants ont aussi été créée.
  - Le graphe affiche le plus court chemin mais sans prendre en compte les **Sortie** directe. 
  - Nous arrivons à afficher tous les segments possibles du graphe en tenant compte des obstacles. (27/10)
  - L’algorithme de plus court chemin est maintenant fonctionnel et est grandement inspiré de l’algorithme de Dijkstra. (28/10) 
  - Nous arrivons à calculer et afficher le plus court chemin entre deux points. (28/10)
  - Il est maintenant possible d'avoir le plus court chemin vers la sortie la plus proche pour chaque point du graphe. (28/10)
- L’algorithme d'intersection entre deux segments est maintenant fonctionnel et testé. (26/10)
- Séparation de la partie physique et graphique du projet. 
  - implique l'implementation des contrôleurs de toutes les class qu'on veut afficher.
- Générailisation des codes une class **MathsCalcule** a donc été créée. (24/10)
- Pour faciliter la lecture on a créée la class **Point** qui nous permet de connaître le x et y d'un point. (27/10)
- Chaque Point possède un Point correspondant au point suivant vers la sortie. (30/10)
  - Ces points suivants sont initialisés lorsqu'on calcule les plus courts chemins du graphe. 
  - Les persos se déplacent de points en points en évitant les obstacles. 
- Gestion totale des obstacles. (31/10)
<br/> 

### Semaine du 02/11
- Test de la gestion des obstacles avec n Personne. 
  - Pour n < 100, tout fonctionne bien. 
  - Pour n > 1000, des fois des erreurs de null pointer. 
- La partie graphique est maintenant complètement séparée de la partie physique. (05/11)
  - Pour chaque classe, on a un controller correspondant qui sert uniquement d'affichage. 
  - La Scene du main est la fenetre graphique de base (le fond). 
  - Le Controller est composé d'un ControllerSalle et d'un ControllerPanel. 
    - Le ControllerSalle est créée grâce à une salle. 
    - C'est à lui qu'on ajoute les ControllerObstacle, ControllerSortie et ControllerPersonne. 
    - Il est composé d'une liste de persos qui sont déplacés suivant les coordCourant de la Personne. 
    - Le ControllerPanel permet l'affichage des boutons play, pause...
  - La Salle est donc maintenant créée dans le main. 
  - Dans le main, on ajoute à la Salle des Obstacle et des Sortie. 
- Le rayon des personnes est pris en compte. 
- Le controllerPanel a de nouveaux boutons (05/11)
  - Un Slider qui permet de changer la vitesse des personnes
  - Une CheckBox pour activer ou désactiver l'affichage du graphe
  - Une checkBox pour activer ou désactiver les collisions
- Détection des collisions entre les personnes (classe salle.java)
- Modification fonction avancer de Personne. L'orsqu'un personne peut avancer jusqu'à son objectif, alors ses coordonnées deviennent ceux de l'objectif. Avant ça, la personne pouvait se déplacer plus loin que son objectif si ca vitesse était grande. 
  - Du coup, plus besoin de environEgale pour capter si on ateint l'objectif ou pas. 
- Ajout fonction addRandomPersonnes(int n), dans Salle, qui permet d'ajouter n Personnes à des positions aléatoires dans la salle. 
  - Ajout de la fonction estDansObstacle(Point point) dans ObstacleRectangle pour éviter d'avoir des persos random dans les obstacles. 
