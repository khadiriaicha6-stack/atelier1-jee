# Atelier JEE : Application web de gestion des commandes (MVC)

Université Abdelmalek Essaadi, FST Tanger, Département Génie Informatique, Cycle LSI.
Atelier JEE, Pr. ELAACHAK Lotfi.

## Objectif
Maîtriser les standards JEE (Servlet, JSP, JavaBean) avec une application web
qui gère les commandes de plusieurs clients.

## Technologies
Java 11, Servlet 4.0, JSP 2.3, JSTL 1.2, CDI (Weld), Maven, Tomcat 9, MySQL.

## Structure
- `ma.fstt.entities` : classes entités (Client, Produit, Commande, LigneDeCommande)
- `ma.fstt.service` : interfaces utilisées par les DAO
- `ma.fstt.dao` : classes DAO (JDBC)
- `src/main/webapp/WEB-INF/views` : pages JSP (espaces client, produit, commande)
- `database.sql` : script de création de la base `gestion_commandes`

## Installation
1. Exécuter `database.sql` dans MySQL (phpMyAdmin ou Workbench).
2. Adapter l'URL, l'utilisateur et le mot de passe MySQL dans la classe de connexion.
3. Déployer sur Tomcat 9 (JDK 11) et ouvrir `http://localhost:8080/atelier1-jee/`.