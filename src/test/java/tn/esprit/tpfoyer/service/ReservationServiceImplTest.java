package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservationServiceImplTest {

    @InjectMocks
    ReservationServiceImpl reservationService;

    @Mock
    ReservationRepository reservationRepository;

    Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = new Reservation();
        reservation.setIdReservation("1");
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);
        reservation.setEtudiants(new HashSet<>(Collections.singletonList(new Etudiant())));
    }

    @Test
    @Order(1)
    void testRetrieveAllReservations() {
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());
        List<Reservation> listReservations = reservationService.retrieveAllReservations();
        assertEquals(0, listReservations.size());
    }

    @Test
    @Order(2)
    void testRetrieveReservation() {
        when(reservationRepository.findById("1")).thenReturn(Optional.of(reservation));
        Reservation foundReservation = reservationService.retrieveReservation("1");
        assertNotNull(foundReservation);
        assertEquals("1", foundReservation.getIdReservation());
    }

    @Test
    @Order(3)
    void testAddReservation() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        Reservation savedReservation = reservationService.addReservation(reservation);
        assertNotNull(savedReservation);
        assertEquals("1", savedReservation.getIdReservation());
    }

    @Test
    @Order(4)
    void testModifyReservation() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        reservation.setEstValide(false);
        Reservation updatedReservation = reservationService.modifyReservation(reservation);
        assertNotNull(updatedReservation);
        assertFalse(updatedReservation.isEstValide());
    }

    @Test
    @Order(5)
    void testRemoveReservation() {
        doNothing().when(reservationRepository).deleteById("1");
        reservationService.removeReservation("1");
        verify(reservationRepository, times(1)).deleteById("1");
    }

    @Test
    @Order(6)
    void testTrouverResSelonDateEtStatus() {
        Date date = new Date();
        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(date, true))
                .thenReturn(Collections.singletonList(reservation));
        List<Reservation> foundReservations = reservationService.trouverResSelonDateEtStatus(date, true);
        assertEquals(1, foundReservations.size());
    }

    @Test
    @Order(7)
    void testTrouverReservationsParEtudiant() {
        Etudiant etudiant = new Etudiant();
        when(reservationRepository.findByEtudiants(etudiant)).thenReturn(Collections.singletonList(reservation));
        List<Reservation> foundReservations = reservationService.trouverReservationsParEtudiant(etudiant);
        assertEquals(1, foundReservations.size());
    }

    @Test
    @Order(8)
    void testCompterTotalReservations() {
        when(reservationRepository.count()).thenReturn(1L);
        long count = reservationService.compterTotalReservations();
        assertEquals(1L, count);
    }

    @Test
    @Order(9)
    void testCompterReservationsParAnnee() {
        when(reservationRepository.findByAnneeUniversitaireAndEstValide(any(Date.class), eq(true)))
                .thenReturn(Collections.singletonList(reservation));
        long count = reservationService.compterReservationsParAnnee(new Date());
        assertEquals(1L, count);
    }

    @Test
    @Order(10)
    void testEtudiantAReservation() {
        Etudiant etudiant = new Etudiant();
        when(reservationRepository.findByEtudiants(etudiant)).thenReturn(Collections.singletonList(reservation));
        boolean hasReservation = reservationService.etudiantAReservation(etudiant, reservation.getAnneeUniversitaire());
        assertTrue(hasReservation);
    }
}
