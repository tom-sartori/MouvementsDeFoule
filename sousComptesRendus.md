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
- Séparation de la partie physique et graphique du projet. 
  - implique l'implementation des contrôleurs de toutes les class qu'on veut afficher.
- Générailisation des codes une class **MathsCalcule** a donc été créée.
- Pour faciliter la lecture on a créée la class **Point** qui nous permet de connaître le x et y d'un point. (27/10)

