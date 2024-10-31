import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        MockitoAnnotations.openMocks(this); // Initialisation des mocks

        // Création d'un bloc d'exemple avec des chambres
        bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(10);

        // Ajouter des chambres au bloc
        Set<Chambre> chambres = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            Chambre chambre = new Chambre();
            chambre.setIdChambre((long) i);
            chambres.add(chambre);
        }
        bloc.setChambres(chambres);
    }

    @Test
    void testCreateBloc() {
        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc);
        assertEquals(bloc.getNomBloc(), savedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testReadBlocById() {
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        Bloc foundBloc = blocService.retrieveBloc(1L);

        assertNotNull(foundBloc);
        assertEquals(bloc.getIdBloc(), foundBloc.getIdBloc());
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateBloc() {
        when(blocRepository.save(bloc)).thenReturn(bloc);
        bloc.setNomBloc("Bloc B");

        Bloc updatedBloc = blocService.modifyBloc(bloc);

        assertNotNull(updatedBloc);
        assertEquals("Bloc B", updatedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testDeleteBloc() {
        when(blocRepository.existsById(1L)).thenReturn(true);
        doNothing().when(blocRepository).deleteById(1L);

        blocService.removeBloc(1L);

        verify(blocRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRetrieveAllBlocs() {
        List<Bloc> blocs = List.of(bloc);
        when(blocRepository.findAll()).thenReturn(blocs);

        List<Bloc> retrievedBlocs = blocService.retrieveAllBlocs();

        assertEquals(1, retrievedBlocs.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveBlocsSelonCapacite() {
        List<Bloc> blocs = List.of(bloc);
        when(blocRepository.findAll()).thenReturn(blocs);

        List<Bloc> filteredBlocs = blocService.retrieveBlocsSelonCapacite(5);

        assertEquals(1, filteredBlocs.size());
        assertEquals("Bloc A", filteredBlocs.get(0).getNomBloc());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testFindBlocsSansFoyer() {
        List<Bloc> blocs = List.of(bloc);
        when(blocRepository.findAllByFoyerIsNull()).thenReturn(blocs);

        List<Bloc> blocsSansFoyer = blocService.trouverBlocsSansFoyer();

        assertEquals(1, blocsSansFoyer.size());
        assertEquals("Bloc A", blocsSansFoyer.get(0).getNomBloc());
        verify(blocRepository, times(1)).findAllByFoyerIsNull();
    }

    @Test
    void testFindBlocsParNomEtCap() {
        List<Bloc> blocs = List.of(bloc);
        when(blocRepository.findAllByNomBlocAndCapaciteBloc("Bloc A", 10)).thenReturn(blocs);

        List<Bloc> result = blocService.trouverBlocsParNomEtCap("Bloc A", 10);

        assertEquals(1, result.size());
        assertEquals("Bloc A", result.get(0).getNomBloc());
        verify(blocRepository, times(1)).findAllByNomBlocAndCapaciteBloc("Bloc A", 10);
    }

    @Test
    void testCountChambresInBloc() {
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        long count = blocService.countChambresInBloc(1L);

        assertEquals(5, count);
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testCountChambresInBloc_NotFound() {
        when(blocRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> blocService.countChambresInBloc(1L));

        assertEquals("Bloc non trouvé", exception.getMessage());
        verify(blocRepository, times(1)).findById(1L);
    }
}
