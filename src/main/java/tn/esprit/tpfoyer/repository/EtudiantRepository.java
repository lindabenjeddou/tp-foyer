package tn.esprit.tpfoyer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entity.Etudiant;

import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    // Rechercher par nom en ignorant la casse
    List<Etudiant> findByReservations_IdReservation(String reservationId);
    Etudiant findEtudiantByCinEtudiant(long cin);

}
