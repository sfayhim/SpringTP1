package pharmacie.dao;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /**
     * Trouve toutes les commandes en cours pour un dispensaire donné
     * Une commande est en cours si sa date d'envoi (envoyele) n'est pas renseignée
     * @param dispensaireCode le code du dispensaire
     * @return la liste des commandes en cours
     */
    List<Commande> findByDispensaireCodeAndEnvoyeleIsNull(Integer dispensaireCode);
    
    /**
     * Calcule le nombre total d'articles déjà commandés par un dispensaire
     * (commandes déjà envoyées, envoyele n'est pas null)
     * @param dispensaireCode le code du dispensaire
     * @return le nombre total d'articles
     */
    @Query("SELECT COALESCE(SUM(l.quantite), 0) FROM Commande c JOIN c.lignes l WHERE c.dispensaire.code = :dispensaireCode AND c.envoyele IS NOT NULL")
    Long countArticlesCommandesParDispensaire(@Param("dispensaireCode") Integer dispensaireCode);

}
