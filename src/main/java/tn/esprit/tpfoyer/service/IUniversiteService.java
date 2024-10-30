package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Universite;

import java.util.List;

public interface IUniversiteService {

    List<Universite> retrieveAllUniversites();
    Universite retrieveUniversite(Long universiteId);
    Universite addUniversite(Universite f);
    void removeUniversite(Long universiteId);
    Universite modifyUniversite(Universite universite);

    // Here we will add later methods calling keywords and methods calling JPQL

    List<Universite> searchUniversites(String nomUniversite, String addresse);

    List<Universite> bulkUpdateUniversites(List<Universite> universites);

    void deleteAllUniversities();


}