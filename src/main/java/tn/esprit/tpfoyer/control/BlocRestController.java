package tn.esprit.tpfoyer.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.service.BlocServiceImpl;
import tn.esprit.tpfoyer.service.IBlocService;

import java.util.List;

@Tag(name = "Gestion Bloc pour l'équipe 4DS9")
@RestController
@AllArgsConstructor
@RequestMapping("/bloc")
public class BlocRestController {

    private final BlocServiceImpl blocService; // Rendre blocService final pour une meilleure immutabilité

    // URL : http://localhost:8089/tpfoyer/bloc/retrieve-all-blocs
    @GetMapping("/retrieve-all-blocs")
    @Operation(description = "WS de récupération de tous les Blocs ")
    public List<Bloc> getBlocs() {
        return blocService.retrieveAllBlocs();
    }

    // URL : http://localhost:8089/tpfoyer/bloc/retrieve-bloc/{bloc-id}
    @GetMapping("/retrieve-bloc/{bloc-id}")
    @Operation(description = "Récupérer un Bloc par son ID")
    public Bloc retrieveBloc(@PathVariable("bloc-id") Long bId) {
        return blocService.retrieveBloc(bId);
    }

    // URL : http://localhost:8089/tpfoyer/bloc/add-bloc
    @PostMapping("/add-bloc")
    @Operation(description = "Ajouter un nouveau Bloc")
    public Bloc addBloc(@RequestBody Bloc c) {
        return blocService.addBloc(c);
    }

    // URL : http://localhost:8089/tpfoyer/bloc/remove-bloc/{bloc-id}
    @DeleteMapping("/remove-bloc/{bloc-id}")
    @Operation(description = "Supprimer un Bloc par son ID")
    public void removeBloc(@PathVariable("bloc-id") Long chId) {
        blocService.removeBloc(chId);
    }

    // URL : http://localhost:8089/tpfoyer/bloc/modify-bloc
    @PutMapping("/modify-bloc")
    @Operation(description = "Modifier un Bloc")
    public Bloc modifyBloc(@RequestBody Bloc c) {
        return blocService.modifyBloc(c);
    }

    // URL : http://localhost:8089/tpfoyer/bloc/trouver-blocs-sans-foyer
    @GetMapping("/trouver-blocs-sans-foyer")
    @Operation(description = "Trouver tous les Blocs sans foyer")
    public List<Bloc> getBlocsWithoutFoyer() {
        return blocService.trouverBlocsSansFoyer();
    }

    // URL : http://localhost:8089/tpfoyer/bloc/get-bloc-nb-c/{nb}/{c}
    @GetMapping("/get-bloc-nb-c/{nb}/{c}")
    @Operation(description = "Récupérer les Blocs par nom et capacité")
    public List<Bloc> recuperBlocsParNomEtCap(
            @PathVariable("nb") String nb,
            @PathVariable("c") long c) {
        return blocService.trouverBlocsParNomEtCap(nb, c);
    }

    // URL : http://localhost:8089/tpfoyer/bloc/count-chambres/{bloc-id}
    @GetMapping("/count-chambres/{bloc-id}")
    @Operation(description = "Compter le nombre de chambres dans un Bloc")
    public long countChambresInBloc(@PathVariable("bloc-id") Long blocId) {
        return blocService.countChambresInBloc(blocId);
    }
}
