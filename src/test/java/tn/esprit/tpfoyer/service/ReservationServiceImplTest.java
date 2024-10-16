package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService; // Le service à tester

    @Mock
    private ReservationRepository reservationRepository; // Le repository utilisé par le service

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialisation des mocks
    }

    @Test
    public void testAddReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        Reservation createdReservation = reservationService.addReservation(reservation);

        // Assert
        assertNotNull(createdReservation);
        verify(reservationRepository, times(1)).save(reservation); // Vérifie que save() a été appelé une fois
    }

    @Test
    public void testRetrieveReservation() {
        // Arrange
        String reservationId = "1";
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        // Act
        Reservation retrievedReservation = reservationService.retrieveReservation(reservationId);

        // Assert
        assertNotNull(retrievedReservation);
        verify(reservationRepository, times(1)).findById(reservationId); // Vérifie que findById() a été appelé une fois
    }

    @Test
    public void testRetrieveAllReservations() {
        // Arrange
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        when(reservationRepository.findAll()).thenReturn(reservations);

        // Act
        List<Reservation> allReservations = reservationService.retrieveAllReservations();

        // Assert
        assertEquals(1, allReservations.size());
        verify(reservationRepository, times(1)).findAll(); // Vérifie que findAll() a été appelé une fois
    }

    @Test
    public void testModifyReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        Reservation updatedReservation = reservationService.modifyReservation(reservation);

        // Assert
        assertNotNull(updatedReservation);
        verify(reservationRepository, times(1)).save(reservation); // Vérifie que save() a été appelé une fois
    }

    @Test
    public void testRemoveReservation() {
        // Arrange
        String reservationId = "1";
        doNothing().when(reservationRepository).deleteById(reservationId); // Simule la suppression

        // Act
        reservationService.removeReservation(reservationId);

        // Assert
        verify(reservationRepository, times(1)).deleteById(reservationId); // Vérifie que deleteById() a été appelé une fois
    }

    @Test
    public void testTrouverResSelonDateEtStatus() {
        // Arrange
        Date date = new Date();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(date, true)).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.trouverResSelonDateEtStatus(date, true);

        // Assert
        assertEquals(1, result.size());
        verify(reservationRepository, times(1)).findAllByAnneeUniversitaireBeforeAndEstValide(date, true);
    }

    @Test
    public void testTrouverReservationsParEtudiant() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        when(reservationRepository.findByEtudiants(etudiant)).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.trouverReservationsParEtudiant(etudiant);

        // Assert
        assertEquals(1, result.size());
        verify(reservationRepository, times(1)).findByEtudiants(etudiant);
    }

    @Test
    public void testCompterTotalReservations() {
        // Arrange
        when(reservationRepository.count()).thenReturn(5L);

        // Act
        long count = reservationService.compterTotalReservations();

        // Assert
        assertEquals(5L, count);
        verify(reservationRepository, times(1)).count();
    }

    @Test
    public void testCompterReservationsParAnnee() {
        // Arrange
        Date anneeUniversitaire = new Date();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        when(reservationRepository.findByAnneeUniversitaireAndEstValide(anneeUniversitaire, true)).thenReturn(reservations);

        // Act
        long count = reservationService.compterReservationsParAnnee(anneeUniversitaire);

        // Assert
        assertEquals(1, count);
        verify(reservationRepository, times(1)).findByAnneeUniversitaireAndEstValide(anneeUniversitaire, true);
    }

    @Test
    public void testEtudiantAReservation() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        Date anneeUniversitaire = new Date();
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation = new Reservation();
        reservation.setAnneeUniversitaire(anneeUniversitaire);
        reservations.add(reservation);
        when(reservationRepository.findByEtudiants(etudiant)).thenReturn(reservations);

        // Act
        boolean result = reservationService.etudiantAReservation(etudiant, anneeUniversitaire);

        // Assert
        assertTrue(result);
        verify(reservationRepository, times(1)).findByEtudiants(etudiant);
    }
}
