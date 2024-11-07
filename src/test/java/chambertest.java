import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.BaseRepository;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@Slf4j
@DataJpaTest
public class chambertest {
    @Mock
    private ChambreRepository chambrerepository;
    @Mock
    private ReservationRepository reservationrepository;
    @Mock
    private BaseRepository baserepository ;

    Bloc bloc = new Bloc(1,"B"); // Assuming 'Bloc' has a constructor that accepts a String or an equivalent field
 // Set the name if there's a setter
    Chambre chambre = new Chambre(1, 123, TypeChambre.DOUBLE, new HashSet<>(),bloc);

    List<Chambre> chambreList = new ArrayList<>() {
        {
            add(new Chambre(2, 123, TypeChambre.SIMPLE, new HashSet<>(), bloc));
            add(new Chambre(3, 124, TypeChambre.DOUBLE, new HashSet<>(), bloc));
        }
    };
    @InjectMocks
    private ChambreServiceImpl chambreserviceimpl;
    @InjectMocks
    private BlocServiceImpl blocserviceimpl;
    @Test
    public void retrieveAllPistesTest() {
        Mockito.when(baserepository.findAll()).thenReturn(chambreList);
        List<Chambre> allchambres = chambreserviceimpl.retrieveAllChambres();
        Assertions.assertTrue(!allchambres.isEmpty());
        Assertions.assertEquals(chambreList.size(), allchambres.size());
    }

    @Test
    public void removechambretest() {
        Integer numeroChambre = 123;

        // Mock the deleteById method on baseRepository
        Mockito.doNothing().when(baserepository).deleteById(numeroChambre);
        Mockito.when(baserepository.existsById(Mockito.any(Integer.class))).thenReturn(true);

        // Call the delete method from your service
        chambreserviceimpl.removeChambre(numeroChambre);

        // Verify that deleteById was called with the expected argument
        Mockito.verify(baserepository, Mockito.times(1)).deleteById(numeroChambre);
        Assertions.assertFalse(chambreList.contains(chambre));
    }
    @Test
    public void addchambretest() {

        Mockito.when(baserepository.save(Mockito.any(Chambre.class))).thenReturn(chambre);

        Chambre addchambre = chambreserviceimpl.addChambre(chambre);
        Assertions.assertEquals(chambre, addchambre);
    }
}