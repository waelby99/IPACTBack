package manajero.xp.manajeroxpmethodology.Controller.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Definition;
import manajero.xp.manajeroxpmethodology.Services.Tutoriel.DefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ManajeroBackend/api/definitions")
@CrossOrigin(origins = "http://localhost:4200")
public class DefinitionController {
    @Autowired
    private DefinitionService definitionService;

    @GetMapping
    public List<Definition> getAllDefinitions() {
        return definitionService.getAllDefinitions();
    }


    // GET definition by ID
    @GetMapping("/{id}")
    public ResponseEntity<Definition> getDefinitionById(@PathVariable("id") String id) {
        Definition definition = definitionService.getDefinitionById(id);
        if (definition != null) {
            return ResponseEntity.ok(definition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST create new definition
    @PostMapping
    public ResponseEntity<Definition> createDefinition(@RequestBody Definition definition) {
        Definition createdDefinition = definitionService.createDefinition(definition);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDefinition);
    }

    // PUT update definition by ID
    @PutMapping("/{id}")
    public ResponseEntity<Definition> updateDefinition(@PathVariable("id") String id, @RequestBody Definition updatedDefinition) {
        Definition definition = definitionService.updateDefinition(id, updatedDefinition);
        if (definition != null) {
            return ResponseEntity.ok(definition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE definition by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDefinition(@PathVariable("id") String id) {
        boolean deleted = definitionService.deleteDefinition(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}