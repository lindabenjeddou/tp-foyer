
/*
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChambreServiceImplTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    private Chambre chambre;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Setup a sample room
        chambre = new Chambre(1, 123, TypeChambre.DOUBLE, 5, "B");
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101);
    }

    @Test
    void testRetrieveChambre() {
        // Mock the repository's findById method to return the sample room
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        // Call the service method
        Chambre result = chambreService.retrieveChambre(1L);

        // Assert the results
        assertNotNull(result);
        assertEquals(1L, result.getIdChambre());
        assertEquals(101, result.getNumeroChambre());

        // Verify that the repository's findById method was called once
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testAddChambre() {
        // Mock the repository's save method
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        // Call the service method
        Chambre result = chambreService.addChambre(chambre);

        // Assert the results
        assertNotNull(result);
        assertEquals(101, result.getNumeroChambre());

        // Verify that the repository's save method was called once
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testRemoveChambre() {
        // Call the service method
        chambreService.removeChambre(1);

        // Verify that the repository's deleteById method was called once
        verify(chambreRepository, times(1)).deleteById(1L);
    }
}
*/