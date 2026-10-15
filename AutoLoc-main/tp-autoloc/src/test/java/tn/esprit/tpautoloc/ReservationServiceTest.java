package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.StatutReservation;
import tn.esprit.tpautoloc.service.IReservationService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private IReservationService reservationService;

    @Test
    void testReservationService() {

        // 1. Créer une réservation
        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.now());
        reservation.setDateFin(LocalDate.now().plusDays(5));
        reservation.setStatut(StatutReservation.EN_ATTENTE);

        // 2. Enregistrer la réservation
        Reservation reservationEnregistree =
                reservationService.create(reservation);

        assertNotNull(reservationEnregistree);
        assertNotNull(reservationEnregistree.getIdReservation());
        assertEquals(LocalDate.now(),
                reservationEnregistree.getDateDebut());
        assertEquals(LocalDate.now().plusDays(5),
                reservationEnregistree.getDateFin());

        // 3. Afficher toutes les réservations
        System.out.println("Liste des réservations : "
                + reservationService.findAll());

        assertTrue(
                reservationService.findAll().stream()
                        .anyMatch(r -> r.getIdReservation()
                                .equals(reservationEnregistree.getIdReservation()))
        );

        // 4. Rechercher la réservation par son ID
        Long id = reservationEnregistree.getIdReservation();
        Reservation reservationRecuperee =
                reservationService.findById(id);

        System.out.println("Réservation récupérée : "
                + reservationRecuperee);

        assertNotNull(reservationRecuperee);
        assertEquals(id, reservationRecuperee.getIdReservation());
        assertEquals(StatutReservation.EN_ATTENTE,
                reservationRecuperee.getStatut());

        // 5. Afficher le nombre de réservations
        System.out.println("Nombre de réservations : "
                + reservationService.findAll().size());

        assertTrue(reservationService.findAll().size() >= 1);

        // 6. Supprimer la réservation
        reservationService.deleteById(id);

        // 7. Vérifier que la réservation a été supprimée
        assertFalse(
                reservationService.findAll().stream()
                        .anyMatch(r -> r.getIdReservation().equals(id))
        );
    }
}