package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.domain.CategorieVehicule;
import tn.esprit.tpautoloc.domain.StatutVehicule;
import tn.esprit.tpautoloc.service.IVehiculeService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VehiculeServiceTest {

    @Autowired
    private IVehiculeService vehiculeService;

    @Test
    void testVehiculeService() {

        // 1. Créer un véhicule
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("TEST-123");
        vehicule.setMarque("Toyota");
        vehicule.setModele("Yaris");
        vehicule.setCategorie(CategorieVehicule.CITADINE);
        vehicule.setTarifJournalier(new BigDecimal("120.00"));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);

        // 2. Enregistrer le véhicule
        Vehicule vehiculeEnregistre = vehiculeService.create(vehicule);

        assertNotNull(vehiculeEnregistre);
        assertNotNull(vehiculeEnregistre.getIdVehicule());
        assertEquals("Toyota", vehiculeEnregistre.getMarque());
        assertEquals(new BigDecimal("120.00"),
                vehiculeEnregistre.getTarifJournalier());

        // 3. Afficher tous les véhicules
        System.out.println("Liste des véhicules : "
                + vehiculeService.findAll());

        assertTrue(
                vehiculeService.findAll().stream()
                        .anyMatch(v -> v.getIdVehicule()
                                .equals(vehiculeEnregistre.getIdVehicule()))
        );

        // 4. Rechercher le véhicule par son ID
        Long id = vehiculeEnregistre.getIdVehicule();
        Vehicule vehiculeRecupere = vehiculeService.findById(id);

        System.out.println("Véhicule récupéré : " + vehiculeRecupere);

        assertNotNull(vehiculeRecupere);
        assertEquals(id, vehiculeRecupere.getIdVehicule());
        assertEquals("Yaris", vehiculeRecupere.getModele());

        // 5. Afficher et vérifier le nombre de véhicules
        System.out.println("Nombre de véhicules : "
                + vehiculeService.findAll().size());

        assertTrue(vehiculeService.findAll().size() >= 1);

        // 6. Supprimer le véhicule
        vehiculeService.deleteById(id);

        // 7. Vérifier que le véhicule n'existe plus
        assertFalse(
                vehiculeService.findAll().stream()
                        .anyMatch(v -> v.getIdVehicule().equals(id))
        );
    }
}