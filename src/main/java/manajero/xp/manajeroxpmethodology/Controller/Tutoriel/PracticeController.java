package manajero.xp.manajeroxpmethodology.Controller.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Practice;
import manajero.xp.manajeroxpmethodology.Services.Tutoriel.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ManajeroBackend/api/practices")
@CrossOrigin(origins = "http://localhost:4200")
public class PracticeController {

    @Autowired
    private PracticeService practiceService;

    @GetMapping
    public List<Practice> getAllPractices() {
        return practiceService.getAllPractices();
    }

    @PostMapping
    public Practice createPractice(@RequestBody Practice practice) {
        return practiceService.createPractice(practice);
    }

    @PutMapping("/{id}")
    public Practice updatePractice(@PathVariable String id, @RequestBody Practice practice) {
        return practiceService.updatePractice(id, practice);
    }

    @DeleteMapping("/{id}")
    public void deletePractice(@PathVariable String id) {
        practiceService.deletePractice(id);
    }
}
