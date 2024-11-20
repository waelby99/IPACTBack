package manajero.xp.manajeroxpmethodology.Controller;

import lombok.AllArgsConstructor;

import manajero.xp.manajeroxpmethodology.Entities.manajero.Project;
import manajero.xp.manajeroxpmethodology.Services.IProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/ManajeroBackend/api/project")
public class ProjectController {
    private final IProjectService iProjectService;

    @GetMapping
    public List<Project> getAllProjects() {
        return iProjectService.getAllProjects();
    }

    @PostMapping
    public Project addProject(@RequestBody Project p) {
        return iProjectService.addProject(p);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable String id) {
        iProjectService.deleteProject(id);
    }

    @PutMapping("/updateProject")
    public Project updateProject(@RequestBody Project p) {
        return iProjectService.updateProject(p);
    }

    @GetMapping("/getProjectById/{id}")
    public Project getProjectById(@PathVariable String id) {
        return iProjectService.getProjectById(id);
    }

}
