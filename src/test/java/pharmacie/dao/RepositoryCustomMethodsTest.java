package pharmacie.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pharmacie.entity.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RepositoryCustomMethodsTest {

    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private MedicamentRepository medicamentRepository;
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private DispensaireRepository dispensaireRepository;


    @Test // Ce test se base uniquement sur les données définies dans data.sql
    public void testMedicamentCustomMethods() {    
        Medicament indisponible = medicamentRepository.findByNom("Lévofloxacine 500mg").orElseThrow();
        Medicament disponible   = medicamentRepository.findByNom("Doliprane Effervescent 1g").orElseThrow();
    
        // Trouve tous les médicaments disponibles
        List<Medicament> disponibles = medicamentRepository.findByIndisponibleFalse();

        assertTrue(disponibles.contains(disponible));
        assertFalse(disponibles.contains(indisponible));        
        assertFalse(disponibles.isEmpty());
    }

    @Test // Ce test crée les enregistrements nécessaires
    public void testCategorieCustomMethods() {
        Categorie c1 = new Categorie();
        c1.setLibelle("AnalgesiquesTest");
        categorieRepository.save(c1);

        Categorie c2 = new Categorie();
        c2.setLibelle("AntibiotiquesTest");
        categorieRepository.save(c2);

        // findByLibelle
        Categorie found = categorieRepository.findByLibelle("AnalgesiquesTest");
        assertNotNull(found);
        assertEquals("AnalgesiquesTest", found.getLibelle());

        // findByLibelleContaining
        List<Categorie> list = categorieRepository.findByLibelleContaining("iquesTest");
        assertEquals(2, list.size());
        assertTrue(list.stream().anyMatch(cat -> cat.getLibelle().equals("AntibiotiquesTest")));
        assertTrue(list.stream().anyMatch(cat -> cat.getLibelle().equals("AnalgesiquesTest")));
    }

    @Test // Ce test se base sur les données définies dans data.sql
    public void testFindCommandesAfterDate() {
        // Teste la méthode findBySaisieleAfter
        // Les commandes dans data.sql ont des dates en janvier et février 2026
        
        // Recherche les commandes après le 1er février 2026
        java.util.Date dateRecherche = java.sql.Date.valueOf("2026-02-01");
        List<Commande> commandesApresDate = commandeRepository.findBySaisieleAfter(dateRecherche);
        
        // Doit trouver les commandes saisies après le 1er février
        assertFalse(commandesApresDate.isEmpty(), "Devrait trouver des commandes après le 1er février 2026");
        
        // Vérifie que toutes les commandes retournées ont bien été saisies après la date
        for (Commande cmd : commandesApresDate) {
            assertTrue(cmd.getSaisiele().after(dateRecherche), 
                "La commande " + cmd.getNumero() + " devrait être après " + dateRecherche);
        }
    }

    @Test // Ce test se base sur les données définies dans data.sql
    public void testFindDispensairesByRegion() {
        // Teste la méthode findByRegion
        
        // Recherche les dispensaires en Île-de-France
        List<Dispensaire> dispensairesIDF = dispensaireRepository.findByRegion("Île-de-France");
        
        // Doit trouver au moins un dispensaire (Centre de Santé Nord)
        assertFalse(dispensairesIDF.isEmpty(), "Devrait trouver au moins un dispensaire en Île-de-France");
        
        // Vérifie que tous les dispensaires sont bien dans la région demandée
        for (Dispensaire disp : dispensairesIDF) {
            assertEquals("Île-de-France", disp.getRegion(), 
                "Le dispensaire " + disp.getNom() + " devrait être en Île-de-France");
        }
        
        // Recherche dans une autre région
        List<Dispensaire> dispensairesGrandEst = dispensaireRepository.findByRegion("Grand Est");
        assertFalse(dispensairesGrandEst.isEmpty(), "Devrait trouver au moins un dispensaire dans le Grand Est");
        
        // Recherche dans la région PACA
        List<Dispensaire> dispensairesPACA = dispensaireRepository.findByRegion("PACA");
        assertFalse(dispensairesPACA.isEmpty(), "Devrait trouver au moins un dispensaire en PACA");
    }


}
