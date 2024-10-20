package tn.esprit.tpfoyer.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j  // Simple Loggining Façade For Java
public class BlocServiceImpl  implements IBlocService {


    BlocRepository blocRepository;

    @Scheduled(fixedRate = 30000) // millisecondes // cron fixedRate
    //@Scheduled(cron="0/15 * * * * *")
    public List<Bloc> retrieveAllBlocs() {

        List<Bloc> listB = blocRepository.findAll();
        log.info("taille totale : " + listB.size());
        for (Bloc b: listB) {
            log.info("Bloc : " + b);
        }

        return listB;
    }

    // Exemple sans Keywords :
    @Transactional
    public List<Bloc> retrieveBlocsSelonCapacite(long c) {

        List<Bloc> listB = blocRepository.findAll();
        List<Bloc> listBselonC = new ArrayList<>();

        for (Bloc b: listB) {
            if (b.getCapaciteBloc()>=c)
                listBselonC.add(b);
        }

        return listBselonC;
    }

    @Transactional
    public Bloc retrieveBloc(Long blocId) {

        return blocRepository.findById(blocId).get();
    }


    public Bloc addBloc(Bloc c) {

        return blocRepository.save(c);
    }

    public Bloc modifyBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    public void removeBloc(Long blocId) {
        blocRepository.deleteById(blocId);
    }



    public List<Bloc> trouverBlocsSansFoyer() {
        return blocRepository.findAllByFoyerIsNull();
    }

    public List<Bloc> trouverBlocsParNomEtCap(String nb, long c) {
        return blocRepository.findAllByNomBlocAndCapaciteBloc(nb,  c);
    }

    @Transactional
public double calculateOccupationRateForBloc(Long blocId) {
    // Récupérer le bloc par son ID
    Bloc bloc = blocRepository.findById(blocId)
        .orElseThrow(() -> new RuntimeException("Bloc non trouvé"));

    // Récupérer la liste des chambres du bloc
    List<Chambre> chambres = bloc.getChambres();
    
    // Vérifier si le bloc contient des chambres
    if (chambres == null || chambres.isEmpty()) {
        throw new RuntimeException("Aucune chambre trouvée pour ce bloc.");
    }

    // Compter le nombre de chambres occupées
    long chambresOccupees = chambres.stream()
        .filter(Chambre::isOccupied)
        .count();

    // Calculer le taux d'occupation
    double tauxOccupation = (double) chambresOccupees / chambres.size() * 100;

    // Retourner le taux d'occupation
    return tauxOccupation;
}


}
