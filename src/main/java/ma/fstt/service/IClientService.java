package ma.fstt.service;

import ma.fstt.entities.Client;
import java.util.List;

public interface IClientService {
    void ajouter(Client c);
    void modifier(Client c);
    void supprimer(int id);
    Client trouverParId(int id);
    List<Client> lister();
}
