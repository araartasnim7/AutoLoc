package tn.esprit.tpautoloc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.service.IClientService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private IClientService clientService;

    @Test
    void testClientService() {

        // 1. Créer le premier client
        Client client1 = new Client();
        client1.setNom("Ben Ali");
        client1.setPrenom("Ahmed");
        client1.setEmail("ahmed.benali.test@example.com");
        client1.setTelephone("20111222");
        client1.setNumPermis("PERMIS001");
        client1.setDataInscription(LocalDate.now());

        // 2. Créer le deuxième client
        Client client2 = new Client();
        client2.setNom("Trabelsi");
        client2.setPrenom("Amira");
        client2.setEmail("amira.trabelsi.test@example.com");
        client2.setTelephone("22123456");
        client2.setNumPermis("PERMIS002");
        client2.setDataInscription(LocalDate.now());

        // 3. Enregistrer les deux clients
        Client clientEnregistre1 = clientService.create(client1);
        Client clientEnregistre2 = clientService.create(client2);

        assertNotNull(clientEnregistre1);
        assertNotNull(clientEnregistre1.getIdClient());
        assertEquals("Ben Ali", clientEnregistre1.getNom());

        assertNotNull(clientEnregistre2);
        assertNotNull(clientEnregistre2.getIdClient());
        assertEquals("Trabelsi", clientEnregistre2.getNom());

        // 4. Afficher et vérifier la liste des clients
        System.out.println("Liste des clients : " + clientService.findAll());

        assertTrue(
                clientService.findAll().stream()
                        .anyMatch(c -> c.getIdClient()
                                .equals(clientEnregistre1.getIdClient()))
        );

        assertTrue(
                clientService.findAll().stream()
                        .anyMatch(c -> c.getIdClient()
                                .equals(clientEnregistre2.getIdClient()))
        );

        // 5. Rechercher un client par son ID
        Long id = clientEnregistre1.getIdClient();
        Client clientRecupere = clientService.findById(id);

        System.out.println("Client récupéré : " + clientRecupere);

        assertNotNull(clientRecupere);
        assertEquals(id, clientRecupere.getIdClient());
        assertEquals("ahmed.benali.test@example.com",
                clientRecupere.getEmail());

        // 6. Afficher le nombre de clients
        System.out.println("Nombre de clients : "
                + clientService.findAll().size());

        assertTrue(clientService.findAll().size() >= 2);

        // 7. Supprimer le premier client
        clientService.deleteById(id);

        // 8. Vérifier que le client supprimé n'existe plus
        assertFalse(
                clientService.findAll().stream()
                        .anyMatch(c -> c.getIdClient().equals(id))
        );

        // 9. Vérifier que le deuxième client existe toujours
        assertNotNull(
                clientService.findById(clientEnregistre2.getIdClient())
        );
    }
}
