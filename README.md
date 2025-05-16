# BookHaven: Mini Bookstore avec Spring Boot

## Description

BookHaven est une application web de librairie en ligne. Elle permet aux utilisateurs de s'inscrire, se connecter, parcourir un catalogue de livres avec des visuels de haute qualité, consulter les détails de chaque livre, gérer un panier avec quantités, saisir des coordonnées de paiement, et recevoir une confirmation d'achat. Les administrateurs peuvent gérer le stock via un espace dédié.

## Technologies

- **Spring Boot**, **Spring MVC**, **Spring Data JPA**, **Spring Security**
- **Thymeleaf** (templates HTML)
- **Bootstrap** (style responsive)
- **H2 Database** (en mémoire)
- **Maven** (gestion des dépendances)
- **i18n** (français/anglais)

## Fonctionnalités

- **Inscription/Connexion** : Gestion des utilisateurs avec Spring Security.
- **Page d'accueil** : Carrousel de catégories (Fiction, Non-Fiction, Fantasy).
- **Catalogue** : Parcourir et rechercher des livres, affiché dans une grille moderne avec gestion des stocks.
- **Détails du livre** : Page dédiée avec description et disponibilité.
- **Panier** : Ajout, modification des quantités, suppression de livres, avec validation du stock.
- **Paiement** : Formulaire pour saisir les coordonnées de paiement et l'adresse de livraison.
- **Confirmation d'achat** : Résumé complet avec quantités et adresse.
- **Espace admin** : Ajouter, modifier, supprimer, réapprovisionner les livres (accessible via rôle ADMIN).
- **Internationalisation** : Support français/anglais avec sélecteur de langue.
- **H2 Database** : Données de test via `data.sql`, console à `/h2-console`.
- **Design** : Interface moderne avec animations, mode sombre, et responsive.

## Installation

1. Clonez ou téléchargez le projet.
2. Assurez-vous d'avoir **Java 17** et **Maven** installés.
3. Exécutez la commande suivante :

   ```bash
   mvn spring-boot:run
   ```
4. Accédez à l'application :
    - Page d'accueil : `http://localhost:8080/`
    - Catalogue : `http://localhost:8080/books`
    - Inscription : `http://localhost:8080/signup`
    - Connexion : `http://localhost:8080/login` (utilisez `user`/`password` ou `admin`/`password`)
    - Espace admin : `http://localhost:8080/admin` (connexion avec `admin`/`password`)
    - Console H2 : `http://localhost:8080/h2-console` (JDBC URL : `jdbc:h2:mem:testdb`, utilisateur : `sa`, mot de passe vide)

## Utilisation

- Visitez la page d'accueil pour découvrir les catégories.
- Inscrivez-vous ou connectez-vous.
- Parcourez le catalogue, consultez les détails, ajoutez au panier avec quantités.
- Modifiez les quantités ou supprimez des livres dans le panier.
- Passez au paiement pour saisir les coordonnées et l'adresse.
- Validez pour voir la confirmation.
- Pour les admins : connectez-vous avec `admin`/`password`, accédez à `/admin` pour gérer les livres.
- Changez la langue via le menu déroulant.
- Activez le mode sombre avec le bouton en bas à droite.

## Structure

- `src/main/java/com/bookhaven/` : Code Java (contrôleurs, services, entités, repositories).
- `src/main/resources/templates/` : Templates Thymeleaf (`home.html`, `admin.html`, etc.).
- `src/main/resources/static/` : CSS et JS pour le style et les animations.
- `src/main/resources/i18n/` : Fichiers de traduction (`messages_fr.properties`, `messages_en.properties`).
- `src/main/resources/data.sql` : Données de test avec images, descriptions, et quantités.

## Avertissement

Ce projet a été créé pour un usage académique. 
