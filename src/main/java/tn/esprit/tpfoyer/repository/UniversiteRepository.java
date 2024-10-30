/*package tn.esprit.tpfoyer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entity.Universite;

import java.util.List;

@Repository
public interface UniversiteRepository extends JpaRepository<Universite, Long>
{


    List<Universite> searchUniversites(String nomUniversite, String addresse);


    void deleteAllUniversities();
}*/
package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpfoyer.entity.Universite;

import java.util.List;

@Repository
public interface UniversiteRepository extends JpaRepository<Universite, Long> {

    // Custom search method with partial matching on nomUniversite and adresse
    @Query("SELECT u FROM Universite u WHERE u.nomUniversite LIKE %:nomUniversite% AND u.adresse LIKE %:adresse%")
    List<Universite> searchUniversites(@Param("nomUniversite") String nomUniversite, @Param("adresse") String adresse);

    // Custom delete method to remove all universities
    @Modifying
    @Transactional
    @Query("DELETE FROM Universite")
    void deleteAllUniversities();
}