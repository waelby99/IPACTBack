package manajero.xp.manajeroxpmethodology.Controller.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Impact;
import manajero.xp.manajeroxpmethodology.Services.Tutoriel.ImpactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ManajeroBackend/api/impacts")
@CrossOrigin(origins = "http://localhost:4200")
public class ImpactController {
    @Autowired
    private ImpactService impactService;

    @GetMapping
    public List<Impact> getAllImpacts() {
        return impactService.getAllImpacts();
    }


    @PostMapping(consumes = "multipart/form-data")
    public Impact createImpact(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestPart("file") MultipartFile file) throws IOException {
        return impactService.createImpact(name, description, file);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Impact> updateImpact(
            @PathVariable String id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Impact impact = impactService.updateImpact(id, name, description, file);
        return ResponseEntity.ok(impact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImpact(@PathVariable String id) {
        impactService.deleteImpact(id);
        return ResponseEntity.noContent().build();
    }
}
