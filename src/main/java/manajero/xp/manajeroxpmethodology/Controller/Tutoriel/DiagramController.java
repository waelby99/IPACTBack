package manajero.xp.manajeroxpmethodology.Controller.Tutoriel;


import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Diagram;
import manajero.xp.manajeroxpmethodology.Services.Tutoriel.DiagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ManajeroBackend/api/diagrams")
@CrossOrigin(origins = "http://localhost:4200")
public class DiagramController {
    @Autowired
    private DiagramService diagramService;

    @GetMapping
    public List<Diagram> getAllDiagrams() {
        return diagramService.getAllDiagrams();
    }

    @GetMapping("/{id}")
    public Optional<Diagram> getDiagramById(@PathVariable String id) {
        return diagramService.getDiagramById(id);
    }
    @PostMapping(consumes = "multipart/form-data")
    public Diagram createDiagram(
            @RequestParam("name") String name,
            @RequestPart("file") MultipartFile file) throws IOException {
        return diagramService.saveDiagram(name, file);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Diagram> updateDiagram(
            @PathVariable String id,
            @RequestParam("name") String name,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Diagram diagram = diagramService.updateDiagram(id, name, file);
        return ResponseEntity.ok(diagram);
    }

    @DeleteMapping("/{id}")
    public void deleteDiagram(@PathVariable String id) {
        diagramService.deleteDiagram(id);
    }
}
