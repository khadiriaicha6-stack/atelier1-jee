package ma.fstt.service;

import ma.fstt.entities.Commande;
import java.util.List;

public interface ICommandeService {
    void ajouter(Commande c);
    void modifier(Commande c);
    void supprimer(int id);
    Commande trouverParId(int id);
    List<Commande> lister();
}
