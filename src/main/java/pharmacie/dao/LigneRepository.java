package pharmacie.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import pharmacie.entity.Ligne;


// This will be AUTO IMPLEMENTED by Spring into a Bean called ProductCodeRepository
// CRUD refers Create, Read, Update, Delete

/**
 * Un repository avec des méthodes de recherche spécifiques, auto-implémentées par Spring
 */
@Repository
public interface LigneRepository extends JpaRepository<Ligne, Integer> {
	
	/**
	 * Trouve toutes les lignes pour une commande donnée
	 * @param commandeNumero le numéro de la commande
	 * @return la liste des lignes de cette commande
	 */
	List<Ligne> findByCommandeNumero(Integer commandeNumero);
	
	/**
	 * Trouve toutes les lignes pour un médicament donné
	 * @param medicamentReference la référence du médicament
	 * @return la liste des lignes contenant ce médicament
	 */
	List<Ligne> findByMedicamentReference(Integer medicamentReference);
	
	/**
	 * Calcule la quantité totale commandée pour un médicament
	 * @param medicamentReference la référence du médicament
	 * @return la quantité totale
	 */
	@Query("SELECT COALESCE(SUM(l.quantite), 0) FROM Ligne l WHERE l.medicament.reference = :medicamentReference")
	Long sumQuantiteByMedicamentReference(@Param("medicamentReference") Integer medicamentReference);

}
