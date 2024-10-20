package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.FoyerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoyerServiceImplTest {

    @Mock
    FoyerRepository foyerRepository;

    @InjectMocks
    FoyerServiceImpl foyerService;

    Foyer foyer;

    Universite universite;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialisation d'un objet Foyer pour les tests
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(100L);

        // Initialisation d'un objet Universite pour les tests
        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Universite X");
    }

    @Test
    void testRetrieveAllFoyers() {
        // Création d'une liste fictive de foyers
        List<Foyer> foyers = new ArrayList<>();
        foyers.add(foyer);

        // Simulation de l'appel du repository
        when(foyerRepository.findAll()).thenReturn(foyers);

        // Appel du service
        List<Foyer> result = foyerService.retrieveAllFoyers();

        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Foyer A", result.get(0).getNomFoyer());

        // Vérification de l'appel du repository
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveFoyer() {
        // Simulation de l'appel du repository
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Appel du service
        Foyer result = foyerService.retrieveFoyer(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals(1L, result.getIdFoyer());
        assertEquals("Foyer A", result.getNomFoyer());

        // Vérification de l'appel du repository
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void testAddFoyer() {
        // Simulation de l'enregistrement d'un foyer
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Appel du service
        Foyer result = foyerService.addFoyer(foyer);

        // Vérifications
        assertNotNull(result);
        assertEquals("Foyer A", result.getNomFoyer());

        // Vérification de l'appel du repository
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testModifyFoyer() {
        // Simulation de la modification d'un foyer
        Foyer modifiedFoyer = new Foyer(1L, "Foyer B", 150L, null, null);
        when(foyerRepository.save(modifiedFoyer)).thenReturn(modifiedFoyer);

        // Appel du service
        Foyer result = foyerService.modifyFoyer(modifiedFoyer);

        // Vérifications
        assertNotNull(result);
        assertEquals("Foyer B", result.getNomFoyer());
        assertEquals(150L, result.getCapaciteFoyer());

        // Vérification de l'appel du repository
        verify(foyerRepository, times(1)).save(modifiedFoyer);
    }

    @Test
    void testAssignUniversiteToFoyer() {
        // Simulation de la recherche du foyer par ID
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Simulation de la sauvegarde du foyer avec l'université
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Appel de la méthode assignUniversiteToFoyer
        Foyer result = foyerService.assignUniversiteToFoyer(1L, universite);

        // Vérifications
        assertNotNull(result);
        assertEquals("Universite X", result.getUniversite().getNomUniversite());
        assertEquals("Foyer A", result.getUniversite().getFoyer().getNomFoyer());

        // Vérification de l'appel du repository
        verify(foyerRepository, times(1)).findById(1L);
        verify(foyerRepository, times(1)).save(foyer);
    }


}
