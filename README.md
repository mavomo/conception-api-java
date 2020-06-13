# Super hero Training Application

## Avant de commencer 
Il est essentiel de vérifier que l'existant fonctionne sur votre poste.

### Déploiement de l'application

* Depuis votre terminal, executez la commande :  

        ``mvn spring:boot-run``

- [x] Vérifiez bien que vous lancez la commande depuis la racine du projet.

Vous devriez avoir le message ci-dessous dans le terminal:


    ... superhero.SuperHeroApplication   : Started SuperHeroApplication in 8.19 seconds (JVM running for 8.888) 


* Depuis votre navigateur, allez à l'addresse: `` http://localhost:8080/``

Vous devez avoir une page comme ci-dessous: 
![image info](src/main/resources/assets/welcome_page_it_works.JPG)

### Vérification des données initiales
Au démarrage, nous initialisons la table des super héros grâce à [liquibase](https://www.liquibase.org/).

Nous utilisons [H2](https://h2database.com/html/main.html) pour la persistance ( éphémère) des données en mémoire.
* Vérifiez que les données initiales ont bien été chargées:

    * Aller à l'addresse: `` http://localhost:8080/h2-console``
    
    * Connectez-vous à la base de données: 
    ![image info](src/main/resources/assets/super-heroes-h2.JPG)
- [x] Vérifiez bien que la valeur du champ JDBC URL. Elle doit être égale à:  *jdbc:h2:mem:super-hero-app*
   
    * Lister les entrées de la table super_hero:     `` select * from super_hero``
       ![image info](src/main/resources/assets/listing_heroes.JPG)

- [x] Tout marche bien ? Chouette ! Tous les tests sont-ils au vert ?

### Execution des tests
 
* Depuis votre terminal, executez la commande :  

        ``mvn clean test``

- [x] Vérifiez bien que vous lancez la commande depuis la racine du projet.

Vous devriez avoir le message ci-dessous dans le terminal:

    ``
    ...
    [INFO] Results:
    [INFO]
    [INFO] Tests run: 25, Failures: 0, Errors: 0, Skipped: 0
    [INFO]
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    ...
    
``
Si vous choisissez de lancer les tests depuis votre IDE ( clic droit > run all tests), vous verrez la barre verte \o/ 
![image info](src/main/resources/assets/all_green.JPG)

## TP 1 : Définir vos APIs

Avec votre équipe métier, vous avez défini vos endpoints. Il vous faut désormais les implementer !

Pour rappel, vous avez défini les choix suivant :

Tout vos services seront exposé en V1 sur `/api/v1`

* `/super-heros` :
  * `GET` : Liste tous les super héros dans la base de données
  * `POST` : Créé un super héros
* `/super-heros/{uuid}`
  * `GET` : Récupère un super héros
  * `PUT` : Met à jour un super héros
* `/missions` :
  * `GET` : Liste toutes les missions
  * `POST` : Créé une nouvelle mission (une mission doit être rattaché à un super heros)
* `/missions/{uuid}`
  * `GET` : Récupére une mission
* `/missions/{uuid}/history-events`
  * `GET` : Liste l'historique des événements d'une mission
  * `POST` : Rajoute une nouvelle évènement dans l'historique d'une mission
