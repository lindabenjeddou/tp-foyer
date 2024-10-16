
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.tpfoyer.entity.Etudiant;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class EtudiantTest {
    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("Doe");
        etudiant.setPrenomEtudiant("John");
        etudiant.setCinEtudiant(12345678L);
        etudiant.setDateNaissance(new Date());
    }

    @Test
    void testGetters() {
        assertEquals(1L, etudiant.getIdEtudiant());
        assertEquals("Doe", etudiant.getNomEtudiant());
        assertEquals("John", etudiant.getPrenomEtudiant());
        assertEquals(12345678L, etudiant.getCinEtudiant());
    }

    @Test
    void testSetters() {
        etudiant.setNomEtudiant("Smith");
        assertEquals("Smith", etudiant.getNomEtudiant());
    }

}
