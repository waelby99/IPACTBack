package manajero.xp.manajeroxpmethodology.Services;

import lombok.AllArgsConstructor;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Project;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Status;
import manajero.xp.manajeroxpmethodology.Repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProjectServiceImp implements IProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public Project addProject(Project p) {
        return projectRepository.save(p);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(String id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Project updateProject(Project p) {
        return projectRepository.save(p);
    }

    @Override
    public Map<String, Double> getProjectProgressOverview(String projectId) {
        Project project = projectRepository.findProjectWithIterations(projectId);
        Map<String, Double> progressOverview = new HashMap<>();

        long totalIterations = project.getIterations().size();
        long completedIterations = project.getIterations().stream()
                .filter(iteration -> iteration.getUserStories().stream()
                        .allMatch(us -> us.getStatus() == Status.DONE))
                .count();

        progressOverview.put("Total Iterations", (double) totalIterations);
        progressOverview.put("Completed Iterations", (double) completedIterations);
        progressOverview.put("Project Progress", totalIterations > 0 ? (double) completedIterations / totalIterations * 100 : 0);

        return progressOverview;

    }
}
