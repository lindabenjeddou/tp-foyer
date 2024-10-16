
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class EtudiantServiceImplTest {
    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllEtudiants() {
        // Arrange
        List<Etudiant> mockEtudiants = new ArrayList<>();
        mockEtudiants.add(new Etudiant(1L, "John", "Doe", 12345678L, null, null));
        mockEtudiants.add(new Etudiant(2L, "Jane", "Doe", 87654321L, null, null));

        when(etudiantRepository.findAll()).thenReturn(mockEtudiants);

        // Act
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Assert
        assertEquals(2, result.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveEtudiant() {
        // Arrange
        Etudiant mockEtudiant = new Etudiant(1L, "John", "Doe", 12345678L, null, null);
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(mockEtudiant));

        // Act
        Etudiant result = etudiantService.retrieveEtudiant(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void testAddEtudiant() {
        // Arrange
        Etudiant newEtudiant = new Etudiant(0L, "John", "Doe", 12345678L, null, null);
        when(etudiantRepository.save(newEtudiant)).thenReturn(newEtudiant);

        // Act
        Etudiant result = etudiantService.addEtudiant(newEtudiant);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(newEtudiant);
    }

    @Test
    void testRemoveEtudiant() {
        // Act
        etudiantService.removeEtudiant(1L);

        // Assert
        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRecupererEtudiantParCin() {
        // Arrange
        Etudiant mockEtudiant = new Etudiant(1L, "John", "Doe", 12345678L, null, null);
        when(etudiantRepository.findEtudiantByCinEtudiant(12345678L)).thenReturn(mockEtudiant);

        // Act
        Etudiant result = etudiantService.recupererEtudiantParCin(12345678L);

        // Assert
        assertNotNull(result);
        assertEquals(12345678L, result.getCinEtudiant());
        verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(12345678L);
    }
}

