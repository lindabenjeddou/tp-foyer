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
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j  // Simple Logging Façade for Java
public class BlocServiceImpl implements IBlocService {

    private final BlocRepository blocRepository;

    @Scheduled(fixedRate = 30000) // milliseconds, cron fixedRate
    //@Scheduled(cron="0/15 * * * * *")
    public List<Bloc> retrieveAllBlocs() {
        List<Bloc> listB = blocRepository.findAll();
        log.info("Total size: " + listB.size());
        for (Bloc b : listB) {
            log.info("Bloc: " + b);
        }
        return listB;
    }

    @Override
    public Bloc retrieveBloc(int blocId) {
        return null;
    }

    // Example without keywords
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

   // @Transactional
    //public Optional<Bloc> retrieveBloc(int blocId) {
        // Corrected to return Optional<Bloc> and handle findById correctly
        //return blocRepository.findById(blocId);
    //}

    public Bloc addBloc(Bloc c) {
        return blocRepository.save(c);
    }

    @Override
    public void removeBloc(int blocId) {

    }

    public Bloc modifyBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }


    public List<Bloc> trouverBlocsSansFoyer() {
        return blocRepository.findAllByFoyerIsNull();
    }

    @Override
    public List<Bloc> trouverBlocsParNomEtCap(String nb, int c) {
        return blocRepository.findAllByNomBlocAndCapaciteBloc(nb, c);
    }
}
