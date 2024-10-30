package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UniversiteServiceImpl implements IUniversiteService {

    UniversiteRepository universiteRepository;

    public List<Universite> retrieveAllUniversites() {
        return universiteRepository.findAll();
    }

    public Universite retrieveUniversite(Long universiteId) {
        return universiteRepository.findById(universiteId).get();
    }

    public Universite addUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    public void removeUniversite(Long universiteId) {
        universiteRepository.deleteById(universiteId);
    }
    public Universite modifyUniversite(Universite universite) {
        return universiteRepository.save(universite);
    }

    //My SearchUniversite methode
    @Override
    public List<Universite> searchUniversites(String nomUniversite, String addresse) {
        return universiteRepository.searchUniversites(nomUniversite, addresse);
    }

    //My bulkUpdateUniversites methode
    @Override
    public List<Universite> bulkUpdateUniversites(List<Universite> universites) {
        return universiteRepository.saveAll(universites);
    }

    //My deleteAllUniversites methode
    @Override
    public void deleteAllUniversities() {
        universiteRepository.deleteAllUniversities();
    }
}