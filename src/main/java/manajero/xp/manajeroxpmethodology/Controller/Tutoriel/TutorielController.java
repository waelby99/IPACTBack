package manajero.xp.manajeroxpmethodology.Controller.Tutoriel;
import manajero.xp.manajeroxpmethodology.Services.TutorielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ManajeroBackend/api/tutoriels")
@CrossOrigin(origins = "http://localhost:4200")
public class TutorielController {

    @Autowired
    private TutorielService tutorielService;

    @DeleteMapping("/deleteAll")
    public void deleteAllDocuments() {
        tutorielService.deleteAllDocuments();
    }
}
