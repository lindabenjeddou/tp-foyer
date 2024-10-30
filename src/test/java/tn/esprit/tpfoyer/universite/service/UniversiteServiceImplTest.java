package tn.esprit.tpfoyer.universite.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UniversiteServiceImplTest {

    @Mock
    UniversiteRepository universiteRepository;

    @InjectMocks
    UniversiteServiceImpl universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchUniversites() {
        // Given
        Universite u1 = new Universite(1L, "ESPRIT", "Tunis", null);
        Universite u2 = new Universite(2L, "ENIG", "Gabès", null);
        List<Universite> expectedUniversities = Arrays.asList(u1, u2);

        // When
        when(universiteRepository.searchUniversites("ESPRIT", "Tunis")).thenReturn(expectedUniversities);

        // Execute the service method
        List<Universite> actualUniversities = universiteService.searchUniversites("ESPRIT", "Tunis");

        // Then
        assertNotNull(actualUniversities);
        assertEquals(2, actualUniversities.size());
        assertEquals("ESPRIT", actualUniversities.get(0).getNomUniversite());

        // Verify the repository method was called
        verify(universiteRepository, times(1)).searchUniversites("ESPRIT", "Tunis");
    }
    @Test
    void testBulkUpdateUniversites() {
        // Given
        Universite u1 = new Universite(1L, "ESPRIT", "Tunis", null);
        Universite u2 = new Universite(2L, "ENIG", "Gabès", null);
        List<Universite> universitiesToUpdate = Arrays.asList(u1, u2);

        // When
        when(universiteRepository.saveAll(universitiesToUpdate)).thenReturn(universitiesToUpdate);

        // Execute the service method
        List<Universite> updatedUniversities = universiteService.bulkUpdateUniversites(universitiesToUpdate);

        // Then
        assertNotNull(updatedUniversities);
        assertEquals(2, updatedUniversities.size());
        assertEquals("ESPRIT", updatedUniversities.get(0).getNomUniversite());
        assertEquals("ENIG", updatedUniversities.get(1).getNomUniversite());

        // Verify the repository method was called
        verify(universiteRepository, times(1)).saveAll(universitiesToUpdate);
    }

    @Test
    void testBulkUpdateUniversites_EmptyList() {
        // Given
        List<Universite> emptyList = List.of();

        // When
        when(universiteRepository.saveAll(emptyList)).thenReturn(emptyList);

        // Execute the service method
        List<Universite> updatedUniversities = universiteService.bulkUpdateUniversites(emptyList);

        // Then
        assertNotNull(updatedUniversities);
        assertTrue(updatedUniversities.isEmpty());

        // Verify the repository method was called
        verify(universiteRepository, times(1)).saveAll(emptyList);
    }
    @Test
    void testDeleteAllUniversities() {
        // When
        universiteService.deleteAllUniversities();

        // Then
        verify(universiteRepository, times(1)).deleteAllUniversities();// Verify the custom method is called
    }
}
