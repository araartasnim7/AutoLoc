package tn.esprit.tpautoloc;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Vehicule;

import tn.esprit.tpautoloc.domain.CategorieVehicule;
import tn.esprit.tpautoloc.domain.StatutVehicule;

import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.repository.IClientRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RepositoryTest {

    @Autowired
    private IAgenceRepository agenceRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IVehiculeRepository vehiculeRepository;


    // ==============================
    // PARTIE 2 : TEST AGENCE
    // ==============================
    @Test
    void testAgenceRepository() {

        // 1. Créer une agence
        Agence agence = new Agence();
        agence.setNom("Agence Test");
        agence.setVille("Tozeur");
        agence.setAdresses("Centre-ville");
        agence.setTelephone("71234567");

        // 2. Enregistrer l'agence
        Agence agenceEnregistree = agenceRepository.save(agence);

        System.out.println("\n===== TEST AGENCE =====");
        System.out.println("Agence enregistrée : " + agenceEnregistree);

        assertNotNull(agenceEnregistree.getIdAgence());

        Long idAgence = agenceEnregistree.getIdAgence();

        // 3. Afficher toutes les agences
        System.out.println("Toutes les agences :");
        System.out.println(agenceRepository.findAll());

        // 4. Rechercher par identifiant
        Agence agenceTrouvee =
                agenceRepository.findById(idAgence).orElse(null);

        System.out.println("Agence trouvée : " + agenceTrouvee);
        assertNotNull(agenceTrouvee);

        // 5. Vérifier l'existence
        assertTrue(agenceRepository.existsById(idAgence));
        System.out.println("Agence existante : "
                + agenceRepository.existsById(idAgence));

        // 6. Afficher le nombre d'agences
        System.out.println("Nombre d'agences : "
                + agenceRepository.count());

        // 7. Supprimer l'agence
        agenceRepository.deleteById(idAgence);

        assertFalse(agenceRepository.existsById(idAgence));
        System.out.println("Agence supprimée : "
                + !agenceRepository.existsById(idAgence));
    }


    // ==============================
    // PARTIE 3 : TEST CLIENT
    // ==============================
    @Test
    void testClientRepository() {

        System.out.println("\n===== TEST CLIENT =====");

        // 1. Créer le premier client
        Client client1 = new Client();
        client1.setNom("Ben Ali");
        client1.setPrenom("Ahmed");
        client1.setEmail("ahmed.test@example.com");
        client1.setTelephone("20111222");
        client1.setNumPermis("PERMIS-TEST-001");
        client1.setDataInscription(LocalDate.now());

        // 2. Créer le deuxième client
        Client client2 = new Client();
        client2.setNom("Trabelsi");
        client2.setPrenom("Amira");
        client2.setEmail("amira.test@example.com");
        client2.setTelephone("22123456");
        client2.setNumPermis("PERMIS-TEST-002");
        client2.setDataInscription(LocalDate.now());

        // 3. Enregistrer les deux clients
        Client clientEnregistre1 = clientRepository.save(client1);
        Client clientEnregistre2 = clientRepository.save(client2);

        System.out.println("Client 1 enregistré : " + clientEnregistre1);
        System.out.println("Client 2 enregistré : " + clientEnregistre2);

        assertNotNull(clientEnregistre1.getIdClient());
        assertNotNull(clientEnregistre2.getIdClient());

        Long idClient = clientEnregistre1.getIdClient();

        // 4. Afficher tous les clients
        System.out.println("Tous les clients :");
        System.out.println(clientRepository.findAll());

        // 5. Rechercher un client
        Client clientTrouve =
                clientRepository.findById(idClient).orElse(null);

        System.out.println("Client trouvé : " + clientTrouve);
        assertNotNull(clientTrouve);

        // 6. Tester l'existence d'un client existant
        assertTrue(clientRepository.existsById(idClient));
        System.out.println("Client existant : "
                + clientRepository.existsById(idClient));

        // 7. Tester un identifiant inexistant
        Long idInexistant = -1L;

        assertFalse(clientRepository.existsById(idInexistant));
        System.out.println("Client inexistant : "
                + clientRepository.existsById(idInexistant));

        // 8. Afficher le nombre de clients
        System.out.println("Nombre de clients : "
                + clientRepository.count());
    }


    // ==============================
    // PARTIE 4 : TEST VEHICULE
    // ==============================
    @Test
    void testVehiculeRepository() {

        System.out.println("\n===== TEST VEHICULE =====");

        // 1. Créer une agence pour le véhicule
        // Le véhicule est lié à une agence par @ManyToOne.
        Agence agence = new Agence();
        agence.setNom("Agence Véhicule Test");
        agence.setVille("Tozeur");
        agence.setAdresses("Avenue Habib Bourguiba");
        agence.setTelephone("71234567");

        Agence agenceEnregistree = agenceRepository.save(agence);

        // 2. Créer le véhicule
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("TEST-123");
        vehicule.setMarque("Toyota");
        vehicule.setModele("Yaris");
        vehicule.setCategorie(CategorieVehicule.CITADINE);
        vehicule.setTarifJournalier(new BigDecimal("100.00"));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);
        vehicule.setAgence(agenceEnregistree);

        // 3. Enregistrer le véhicule
        Vehicule vehiculeEnregistre = vehiculeRepository.save(vehicule);

        System.out.println("Véhicule enregistré : " + vehiculeEnregistre);
        assertNotNull(vehiculeEnregistre.getIdVehicule());

        Long idVehicule = vehiculeEnregistre.getIdVehicule();

        // 4. Afficher tous les véhicules
        System.out.println("Tous les véhicules :");
        System.out.println(vehiculeRepository.findAll());

        // 5. Afficher le nombre de véhicules
        System.out.println("Nombre de véhicules : "
                + vehiculeRepository.count());

        // 6. Modifier la marque
        vehiculeEnregistre.setMarque("Toyota modifiée");

        // 7. Enregistrer la modification
        vehiculeRepository.save(vehiculeEnregistre);

        // 8. Vérifier la modification
        Vehicule vehiculeModifie =
                vehiculeRepository.findById(idVehicule).orElse(null);

        assertNotNull(vehiculeModifie);
        assertEquals("Toyota modifiée", vehiculeModifie.getMarque());

        System.out.println("Véhicule modifié : " + vehiculeModifie);

        // 9. Supprimer le véhicule
        vehiculeRepository.deleteById(idVehicule);

        assertFalse(vehiculeRepository.existsById(idVehicule));
        System.out.println("Véhicule supprimé : "
                + !vehiculeRepository.existsById(idVehicule));

        // 10. Supprimer l'agence de test
        agenceRepository.deleteById(agenceEnregistree.getIdAgence());
    }
}
