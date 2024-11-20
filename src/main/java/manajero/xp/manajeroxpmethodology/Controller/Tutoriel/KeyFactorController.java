package manajero.xp.manajeroxpmethodology.Controller.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.KeyFactor;
import manajero.xp.manajeroxpmethodology.Services.Tutoriel.KeyFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ManajeroBackend/api/keyfactors")
@CrossOrigin(origins = "http://localhost:4200")
public class KeyFactorController {
    @Autowired
    private KeyFactorService keyFactorService;

    @GetMapping
    public List<KeyFactor> getAllKeyFactors() {
        return keyFactorService.getAllKeyFactors();
    }

    @PostMapping
    public KeyFactor createKeyFactor(@RequestBody KeyFactor keyFactor) {
        return keyFactorService.createKeyFactor(keyFactor);
    }

    @PutMapping("/{id}")
    public KeyFactor updateKeyFactor(@PathVariable String id, @RequestBody KeyFactor keyFactor) {
        return keyFactorService.updateKeyfactor(id, keyFactor);
    }

    @DeleteMapping("/{id}")
    public void deleteKeyFactor(@PathVariable String id) {
        keyFactorService.deleteKeyFactor(id);
    }
}
