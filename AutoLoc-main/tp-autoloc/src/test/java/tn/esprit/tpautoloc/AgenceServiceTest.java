package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.service.IAgenceService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AgenceServiceTest {

    @Autowired
    private IAgenceService agenceService;

    @Test
    void testAgenceService() {

        // 1. Créer une agence
        Agence agence = new Agence();
        agence.setNom("Agence Test");
        agence.setVille("Tunis");
        agence.setAdresses("Centre-ville");
        agence.setTelephone("70123456");

        // 2. Enregistrer l'agence
        Agence agenceEnregistree = agenceService.create(agence);

        assertNotNull(agenceEnregistree);
        assertNotNull(agenceEnregistree.getIdAgence());
        assertEquals("Agence Test", agenceEnregistree.getNom());

        // 3. Vérifier que l'agence existe dans la liste
        assertTrue(
                agenceService.findAll().stream()
                        .anyMatch(a -> a.getIdAgence()
                                .equals(agenceEnregistree.getIdAgence()))
        );

        // 4. Récupérer l'agence par son ID
        Long id = agenceEnregistree.getIdAgence();
        Agence agenceRecuperee = agenceService.findById(id);

        assertNotNull(agenceRecuperee);
        assertEquals(id, agenceRecuperee.getIdAgence());
        assertEquals("Tunis", agenceRecuperee.getVille());

        // 5. Vérifier le nombre d'agences
        assertTrue(agenceService.count() > 0);

        // 6. Supprimer l'agence
        agenceService.deleteById(id);

        // 7. Vérifier que l'agence a été supprimée
        assertFalse(
                agenceService.findAll().stream()
                        .anyMatch(a -> a.getIdAgence().equals(id))
        );
    }
}