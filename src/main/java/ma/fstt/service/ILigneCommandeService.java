package ma.fstt.service;

import ma.fstt.entities.LigneCommande;
import java.util.List;

public interface ILigneCommandeService {
    void ajouter(LigneCommande l);
    void modifier(LigneCommande l);
    void supprimer(int id);
    LigneCommande trouverParId(int id);
    List<LigneCommande> listerParCommande(int commandeId);
}
