📚 Bibliothèque DAEK - Backend
Description
Application backend pour la gestion d'une bibliothèque développée avec Spring Boot. Ce système permet de gérer les livres, les utilisateurs, les emprunts et les réservations.

🛠️ Technologies Utilisées
Java 17
Spring Boot 3.5.6
Spring Data JPA - Pour la persistance des données
Spring Web - Pour les API REST
MySQL - Base de données
Maven - Gestion des dépendances
📋 Prérequis
Java 17 ou supérieur
Maven 3.6+
MySQL 8.0+
Un IDE (IntelliJ IDEA, Eclipse, VS Code)
🚀 Installation
1. Cloner le dépôt
git clone https://github.com/deslannnnnnnd/Bibliotheque.git
cd Bibliotheque
2. Configurer la base de données
Créer une base de données MySQL :

CREATE DATABASE bibliotheque_db;
3. Configurer application.properties
Modifier le fichier src/main/resources/application.properties :

spring.datasource.url=jdbc:mysql://localhost:3306/bibliotheque_db
spring.datasource.username=votre_username
spring.datasource.password=votre_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
4. Lancer l'application
./mvnw spring-boot:run
Ou sur Windows :

mvnw.cmd spring-boot:run
L'application sera accessible sur http://localhost:8080

📊 Modèles de Données
📖 Book (Livre)
Champ	Type	Description
id	Long	Identifiant unique
titre	String	Titre du livre
auteur	String	Auteur du livre
isbn	String	Numéro ISBN
categorie	String	Catégorie du livre
type	String	Type de document
statutDisponibilite	String	Statut (disponible, emprunté, réservé)
dateAjout	Date	Date d'ajout au catalogue
👤 User (Utilisateur)
Champ	Type	Description
id	Long	Identifiant unique
nom	String	Nom de l'utilisateur
email	String	Email
motDePasse	String	Mot de passe
numeroEmploye	String	Numéro d'employé
role	String	Rôle (admin, utilisateur)
codeAdmin	String	Code administrateur
dateInscription	Date	Date d'inscription
📥 Emprunt
Champ	Type	Description
id	Long	Identifiant unique
userId	String	ID de l'utilisateur
bookId	String	ID du livre
dateEmprunt	LocalDate	Date d'emprunt
dateRetourPrevue	LocalDate	Date de retour prévue
dateRetourEffective	LocalDate	Date de retour effective
statut	String	Statut de l'emprunt
amende	BigDecimal	Montant de l'amende
🔖 Reservation
Champ	Type	Description
id	Long	Identifiant unique
userId	String	ID de l'utilisateur
bookId	String	ID du livre
dateReservation	LocalDate	Date de réservation
dateExpiration	LocalDate	Date d'expiration
status	String	Statut de la réservation
dateNotification	LocalDate	Date de notification
🗂️ Structure du Projet
src/
├── main/
│   ├── java/
│   │   └── Bibliotheque_DAEK/
│   │       ├── demo/
│   │       │   └── DemoApplication.java      # Point d'entrée
│   │       ├── Model/                         # Entités JPA
│   │       │   ├── Book.java
│   │       │   ├── User.java
│   │       │   ├── Emprunt.java
│   │       │   └── Reservation.java
│   │       └── Repository/                    # Repositories Spring Data
│   │           ├── BookRepository.java
│   │           ├── UserRepository.java
│   │           ├── EmpruntRepository.java
│   │           └── ReservationRepository.java
│   └── resources/
│       └── application.properties             # Configuration
└── test/
    └── java/
        └── Bibliotheque_DAEK/
            └── demo/
                └── DemoApplicationTests.java
🔧 Développement
Repositories
Les repositories utilisent Spring Data JPA et héritent de JpaRepository, offrant des opérations CRUD automatiques :

BookRepository - Gestion des livres
UserRepository - Gestion des utilisateurs
EmpruntRepository - Gestion des emprunts
ReservationRepository - Gestion des réservations
Prochaines étapes
 Ajouter les contrôleurs REST
 Implémenter l'authentification et l'autorisation
 Ajouter la validation des données
 Implémenter la logique métier (services)
 Ajouter la gestion des exceptions
 Créer les tests unitaires et d'intégration
📝 Licence
Ce projet est développé pour DAEK.

👥 Auteur
DAEK Team

📧 Contact
Pour toute question ou suggestion, n'hésitez pas à ouvrir une issue sur GitHub.

Note: Ce projet est en cours de développement. Les fonctionnalités seront ajoutées progressivement
