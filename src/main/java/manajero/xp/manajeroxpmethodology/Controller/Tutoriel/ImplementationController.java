package manajero.xp.manajeroxpmethodology.Controller.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Implementation;
import manajero.xp.manajeroxpmethodology.Services.Tutoriel.ImplementationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ManajeroBackend/api/implementations")
@CrossOrigin(origins = "http://localhost:4200")
public class ImplementationController {
    @Autowired
    private ImplementationService implementationService;

    @GetMapping
    public List<Implementation> getAllImplementations() {
        return implementationService.getAllImplementations();
    }




    @PostMapping(consumes = "multipart/form-data")
    public Implementation createImplementation(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestPart("file") MultipartFile file) throws IOException {
        return implementationService.createImplementation(name, description, file);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Implementation> updateImplementation(
            @PathVariable String id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Implementation implementation = implementationService.updateImplementation(id, name, description, file);
        return ResponseEntity.ok(implementation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImplementation(@PathVariable String id) {
        implementationService.deleteImplementation(id);
        return ResponseEntity.noContent().build();
    }
}
