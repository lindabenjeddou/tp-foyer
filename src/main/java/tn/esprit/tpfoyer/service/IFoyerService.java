package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.entity.Universite;

import java.util.List;

public interface IFoyerService {

    public List<Foyer> retrieveAllFoyers();
    public Foyer retrieveFoyer(Long foyerId);
    public Foyer addFoyer(Foyer f);
    public void removeFoyer(Long foyerId);
    public Foyer modifyFoyer(Foyer foyer);
    public Foyer assignUniversiteToFoyer(Long foyerId, Universite universite);

    // Here we will add later methods calling keywords and methods calling JPQL

}
