import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlocServiceImplTest {

    @InjectMocks
    private BlocServiceImpl blocService;

    @Mock
    private BlocRepository blocRepository;

    private Bloc bloc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks

        // Création d'un bloc d'exemple avec des chambres
        bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(10);

        // Ajouter des chambres au bloc
        Set<Chambre> chambres = new HashSet<>(); // Utiliser un Set au lieu d'une List
        for (int i = 0; i < 5; i++) {
            Chambre chambre = new Chambre();
            chambre.setIdChambre((long) i);
            chambres.add(chambre);
        }
        bloc.setChambres(chambres); // Appeler setChambres avec un Set
    }

    @Test
    void testCountChambresInBloc() {
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        long count = blocService.countChambresInBloc(1L);

        assertEquals(5, count); // Vérifie que le nombre de chambres est correct
        verify(blocRepository, times(1)).findById(1L); // Vérifie que la méthode a été appelée une fois
    }

    @Test
    void testCountChambresInBloc_NotFound() {
        when(blocRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            blocService.countChambresInBloc(1L);
        });

        assertEquals("Bloc non trouvé", exception.getMessage()); // Vérifie que l'exception est correctement lancée
        verify(blocRepository, times(1)).findById(1L); // Vérifie que la méthode a été appelée une fois
    }
}
