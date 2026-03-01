package pharmacie.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import pharmacie.entity.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests des contraintes d'intégrité et des requêtes personnalisées
 */
@DataJpaTest
public class ContraintesIntegriteTest {

    @Autowired
    private CategorieRepository categorieRepository;
    
    @Autowired
    private MedicamentRepository medicamentRepository;
    
    @Autowired
    private DispensaireRepository dispensaireRepository;
    
    @Autowired
    private CommandeRepository commandeRepository;
    
    @Autowired
    private LigneRepository ligneRepository;

    // ========== TESTS DES CONTRAINTES D'INTÉGRITÉ ==========

    @Test
    public void testMedicamentDoitAvoirCategorie() {
        // Tentative de créer un médicament sans catégorie
        Medicament medicament = new Medicament();
        medicament.setNom("Test Médicament Sans Catégorie");
        medicament.setPrixUnitaire(java.math.BigDecimal.TEN);
        
        // Doit échouer car la catégorie est obligatoire (optional = false)
        assertThrows(Exception.class, () -> {
            medicamentRepository.saveAndFlush(medicament);
        }, "Un médicament sans catégorie devrait lever une exception");
    }

    @Test
    public void testSupprimerCategorieVide() {
        // Créer une catégorie sans médicaments
        Categorie categorie = new Categorie("Catégorie Test Vide");
        categorie.setDescription("Catégorie pour test de suppression");
        categorie = categorieRepository.save(categorie);
        
        Integer categorieId = categorie.getCode();
        
        // Supprimer la catégorie vide doit réussir
        categorieRepository.delete(categorie);
        categorieRepository.flush();
        
        // Vérifier qu'elle est bien supprimée
        assertFalse(categorieRepository.findById(categorieId).isPresent(),
            "La catégorie vide devrait être supprimée");
    }

    @Test
    public void testNePeutPasSupprimerCategorieAvecMedicaments() {
        // Récupérer une catégorie qui a des médicaments (via data.sql)
        Categorie categorie = categorieRepository.findByLibelle("Antalgiques et Antipyrétiques");
        assertNotNull(categorie, "La catégorie devrait exister");
        
        // Vérifier qu'elle a des médicaments
        List<Medicament> medicaments = medicamentRepository.findAll();
        long count = medicaments.stream()
            .filter(m -> m.getCategorie().getCode().equals(categorie.getCode()))
            .count();
        assertTrue(count > 0, "La catégorie devrait avoir des médicaments");
        
        // Tentative de suppression doit échouer
        assertThrows(Exception.class, () -> {
            categorieRepository.delete(categorie);
            categorieRepository.flush();
        }, "Ne devrait pas pouvoir supprimer une catégorie avec des médicaments");
    }

    @Test
    public void testSupprimerCommandeSupprimeLignes() {
        // Créer un dispensaire
        Dispensaire dispensaire = new Dispensaire();
        dispensaire.setNom("Dispensaire Test");
        dispensaire.setRegion("Test Region");
        dispensaire = dispensaireRepository.save(dispensaire);
        
        // Créer une commande
        Commande commande = new Commande();
        commande.setDispensaire(dispensaire);
        commande.setSaisiele(new Date());
        commande.setDestinataire("Test");
        commande = commandeRepository.save(commande);
        
        // Créer des lignes de commande
        Medicament medicament = medicamentRepository.findByNom("Doliprane Effervescent 1g").orElseThrow();
        
        Ligne ligne1 = new Ligne();
        ligne1.setCommande(commande);
        ligne1.setMedicament(medicament);
        ligne1.setQuantite(10);
        commande.getLignes().add(ligne1);
        
        Ligne ligne2 = new Ligne();
        ligne2.setCommande(commande);
        ligne2.setMedicament(medicament);
        ligne2.setQuantite(20);
        commande.getLignes().add(ligne2);
        
        commandeRepository.saveAndFlush(commande);
        
        Integer commandeId = commande.getNumero();
        
        // Compter les lignes avant suppression
        long countAvant = ligneRepository.count();
        assertTrue(countAvant >= 2, "Devrait avoir au moins 2 lignes");
        
        // Supprimer la commande
        commandeRepository.delete(commande);
        commandeRepository.flush();
        
        // Vérifier que les lignes sont supprimées
        long countApres = ligneRepository.count();
        assertTrue(countApres < countAvant, "Les lignes devraient être supprimées avec la commande");
    }

    @Test
    public void testSupprimerDispensaireSupprimerCommandes() {
        // Créer un dispensaire
        Dispensaire dispensaire = new Dispensaire();
        dispensaire.setNom("Dispensaire Test Suppression");
        dispensaire.setRegion("Test Region");
        dispensaire = dispensaireRepository.save(dispensaire);
        
        // Créer des commandes pour ce dispensaire
        Commande commande1 = new Commande();
        commande1.setDispensaire(dispensaire);
        commande1.setSaisiele(new Date());
        commande1.setDestinataire("Test 1");
        dispensaire.getCommandes().add(commande1);
        
        Commande commande2 = new Commande();
        commande2.setDispensaire(dispensaire);
        commande2.setSaisiele(new Date());
        commande2.setDestinataire("Test 2");
        dispensaire.getCommandes().add(commande2);
        
        dispensaireRepository.saveAndFlush(dispensaire);
        
        Integer dispensaireId = dispensaire.getCode();
        
        // Compter les commandes avant suppression
        long countAvant = commandeRepository.count();
        assertTrue(countAvant >= 2, "Devrait avoir au moins 2 commandes");
        
        // Supprimer le dispensaire
        dispensaireRepository.delete(dispensaire);
        dispensaireRepository.flush();
        
        // Vérifier que les commandes sont supprimées
        long countApres = commandeRepository.count();
        assertTrue(countApres < countAvant, "Les commandes devraient être supprimées avec le dispensaire");
    }

    // ========== TESTS DES REQUÊTES PERSONNALISÉES ==========

    @Test
    public void testFindMedicamentsAReapprovisionner() {
        // Tester la requête de la diapo 51
        // Récupérer une catégorie
        Categorie categorie = categorieRepository.findByLibelle("Antalgiques et Antipyrétiques");
        assertNotNull(categorie);
        
        // Chercher les médicaments à réapprovisionner
        List<Medicament> medicaments = medicamentRepository.findMedicamentsAReapprovisionner(categorie.getCode());
        
        // Vérifier que la requête fonctionne (peut retourner une liste vide)
        assertNotNull(medicaments, "La liste ne devrait pas être null");
        
        // Vérifier la condition : unitesEnStock < niveauDeReappro
        for (Medicament m : medicaments) {
            assertTrue(m.getUnitesEnStock() < m.getNiveauDeReappro(),
                "Le médicament " + m.getNom() + " devrait avoir un stock inférieur au niveau de réappro");
        }
    }

    @Test
    public void testCountArticlesCommandesParDispensaire() {
        // Utiliser un dispensaire qui a des commandes envoyées (via data.sql)
        List<Dispensaire> dispensaires = dispensaireRepository.findAll();
        assertTrue(dispensaires.size() > 0, "Devrait avoir au moins un dispensaire");
        
        Dispensaire dispensaire = dispensaires.get(0);
        
        // Compter les articles commandés
        Long count = commandeRepository.countArticlesCommandesParDispensaire(dispensaire.getCode());
        
        assertNotNull(count, "Le comptage ne devrait pas être null");
        assertTrue(count >= 0, "Le comptage devrait être >= 0");
    }

    @Test
    public void testFindCommandesEnCours() {
        // Chercher les commandes en cours pour un dispensaire
        List<Dispensaire> dispensaires = dispensaireRepository.findAll();
        assertTrue(dispensaires.size() > 0, "Devrait avoir au moins un dispensaire");
        
        Dispensaire dispensaire = dispensaires.get(0);
        
        // Trouver les commandes en cours (envoyele = null)
        List<Commande> commandesEnCours = commandeRepository.findByDispensaireCodeAndEnvoyeleIsNull(dispensaire.getCode());
        
        assertNotNull(commandesEnCours, "La liste ne devrait pas être null");
        
        // Vérifier que toutes les commandes retournées ont envoyele = null
        for (Commande c : commandesEnCours) {
            assertNull(c.getEnvoyele(), "La commande " + c.getNumero() + " devrait avoir envoyele = null");
            assertEquals(dispensaire.getCode(), c.getDispensaire().getCode(),
                "La commande devrait appartenir au bon dispensaire");
        }
    }

    @Test
    public void testFindMedicamentsDisponiblesParCategorie() {
        // Récupérer une catégorie
        Categorie categorie = categorieRepository.findByLibelle("Anti-inflammatoires");
        assertNotNull(categorie);
        
        // Chercher les médicaments disponibles
        List<Medicament> medicaments = medicamentRepository
            .findMedicamentsDisponiblesCommande(categorie.getCode());
        
        assertNotNull(medicaments, "La liste ne devrait pas être null");
        
        // Vérifier les conditions
        for (Medicament m : medicaments) {
            assertFalse(m.isIndisponible(), "Le médicament ne devrait pas être indisponible");
            assertTrue(m.getUnitesEnStock() >= m.getUnitesCommandees(),
                "Le stock devrait être >= quantité commandée pour " + m.getNom());
            assertEquals(categorie.getCode(), m.getCategorie().getCode(),
                "Le médicament devrait appartenir à la bonne catégorie");
        }
    }
}
