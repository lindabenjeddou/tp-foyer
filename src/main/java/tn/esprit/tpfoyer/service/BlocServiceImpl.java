package tn.esprit.tpfoyer.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre; // Assurez-vous que cette importation est présente
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j  // Simple Logging Facade For Java
public class BlocServiceImpl implements IBlocService {

    private final BlocRepository blocRepository; // Rendre le champ final pour une meilleure immutabilité

    @Scheduled(fixedRate = 30000) // Millisecondes // Cron fixedRate
    public List<Bloc> retrieveAllBlocs() {
        List<Bloc> listB = blocRepository.findAll();
        log.info("Taille totale : {}", listB.size()); // Utilisation de la syntaxe de formatage
        for (Bloc b : listB) {
            log.info("Bloc : {}", b);
        }
        return listB;
    }

    // Exemple sans keywords :
    @Transactional
    public List<Bloc> retrieveBlocsSelonCapacite(long c) {
        List<Bloc> listB = blocRepository.findAll();
        List<Bloc> listBselonC = new ArrayList<>();

        for (Bloc b : listB) {
            if (b.getCapaciteBloc() >= c) {
                listBselonC.add(b);
            }
        }
        return listBselonC;
    }

    @Transactional
    public Bloc retrieveBloc(Long blocId) {
        return blocRepository.findById(blocId)
                .orElseThrow(() -> new RuntimeException("Bloc non trouvé")); // Ajout de gestion d'exception
    }

    public Bloc addBloc(Bloc c) {
        return blocRepository.save(c);
    }

    public Bloc modifyBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    public void removeBloc(Long blocId) {
        if (!blocRepository.existsById(blocId)) {
            throw new RuntimeException("Bloc non trouvé pour suppression");
        }
        blocRepository.deleteById(blocId);
    }

    public List<Bloc> trouverBlocsSansFoyer() {
        return blocRepository.findAllByFoyerIsNull();
    }

    public List<Bloc> trouverBlocsParNomEtCap(String nb, long c) {
        return blocRepository.findAllByNomBlocAndCapaciteBloc(nb, c);
    }

    @Transactional
    public long countChambresInBloc(Long blocId) {
        // Récupérer le bloc par son ID
        Bloc bloc = blocRepository.findById(blocId)
                .orElseThrow(() -> new RuntimeException("Bloc non trouvé"));

        // Récupérer la liste des chambres du bloc
        List<Chambre> chambres = new ArrayList<>(bloc.getChambres()); // Assurez-vous que getChambres() renvoie une Collection

        // Retourner le nombre de chambres
        return chambres.size(); // Retourne 0 si aucune chambre
    }
}
