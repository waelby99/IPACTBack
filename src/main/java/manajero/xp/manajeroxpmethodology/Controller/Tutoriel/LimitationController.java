package manajero.xp.manajeroxpmethodology.Controller.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Limitation;
import manajero.xp.manajeroxpmethodology.Services.Tutoriel.LimitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ManajeroBackend/api/limitations")
@CrossOrigin(origins = "http://localhost:4200")
public class LimitationController {
    @Autowired
    private LimitationService limitationService;

    @GetMapping
    public List<Limitation> getAllLimitations() {
        return limitationService.getAllLimitations();
    }


    @PostMapping(consumes = "multipart/form-data")
    public Limitation createLimitation(
            @RequestParam("description") String description,
            @RequestPart("file") MultipartFile file) throws IOException {
        return limitationService.createLimitation(description,file);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Limitation> updateLimitation(
            @PathVariable String id,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Limitation limitation = limitationService.updateLimitation(id,  description, file);
        return ResponseEntity.ok(limitation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLimitation(@PathVariable String id) {
        limitationService.deleteLimitation(id);
        return ResponseEntity.noContent().build();
    }
}
