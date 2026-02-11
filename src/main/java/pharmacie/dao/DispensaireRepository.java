package pharmacie.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import pharmacie.entity.Dispensaire;


// This will be AUTO IMPLEMENTED by Spring into a Bean called ProductCodeRepository
// CRUD refers Create, Read, Update, Delete

/**
 * Un repository avec des méthodes de recherche spécifiques, auto-implémentées par Spring
 */
@Repository
public interface DispensaireRepository extends JpaRepository<Dispensaire, Integer> {
    
    /**
     * Trouve tous les dispensaires dans une région donnée
     * @param region la région à rechercher
     * @return la liste des dispensaires dans cette région
     */
    List<Dispensaire> findByRegion(String region);

}
