DROP DATABASE IF EXISTS gestion_commandes;
CREATE DATABASE gestion_commandes CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE gestion_commandes;

CREATE TABLE client (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nom VARCHAR(100) NOT NULL,
                        prenom VARCHAR(100) NOT NULL,
                        email VARCHAR(150),
                        telephone VARCHAR(30),
                        adresse VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE produit (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         designation VARCHAR(150) NOT NULL,
                         prix DECIMAL(10,2) NOT NULL,
                         quantite_stock INT NOT NULL DEFAULT 0
) ENGINE=InnoDB;

CREATE TABLE commande (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          date_commande DATE NOT NULL,
                          client_id INT NOT NULL,
                          CONSTRAINT fk_commande_client FOREIGN KEY (client_id)
                              REFERENCES client(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE ligne_commande (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                commande_id INT NOT NULL,
                                produit_id INT NOT NULL,
                                quantite INT NOT NULL,
                                prix_unitaire DECIMAL(10,2) NOT NULL,
                                CONSTRAINT fk_ligne_commande FOREIGN KEY (commande_id)
                                    REFERENCES commande(id) ON DELETE CASCADE,
                                CONSTRAINT fk_ligne_produit FOREIGN KEY (produit_id)
                                    REFERENCES produit(id)
) ENGINE=InnoDB;