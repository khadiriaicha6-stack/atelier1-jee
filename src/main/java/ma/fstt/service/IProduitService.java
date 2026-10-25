package ma.fstt.service;

import ma.fstt.entities.Produit;
import java.util.List;

public interface IProduitService {
    void ajouter(Produit p);
    void modifier(Produit p);
    void supprimer(int id);
    Produit trouverParId(int id);
    List<Produit> lister();
}
