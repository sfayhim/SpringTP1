package pharmacie.dao;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import pharmacie.entity.Commande;


// This will be AUTO IMPLEMENTED by Spring into a Bean called ProductCodeRepository
// CRUD refers Create, Read, Update, Delete

/**
 * Un repository avec des méthodes de recherche spécifiques, auto-implémentées par Spring
 */
@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    
    /**
     * Trouve toutes les commandes saisies après une date donnée
     * @param date la date à partir de laquelle rechercher
     * @return la liste des commandes saisies après cette date
     */
    List<Commande> findBySaisieleAfter(Date date);

}
