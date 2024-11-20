package manajero.xp.manajeroxpmethodology.Services;

import manajero.xp.manajeroxpmethodology.Entities.manajero.Project;

import java.util.List;
import java.util.Map;

public interface IProjectService {
    public Project addProject(Project project);
    public List<Project> getAllProjects();
    public Project getProjectById(String id);
    public void deleteProject(String id);
    public Project updateProject(Project project);
    public Map<String, Double> getProjectProgressOverview(String projectId);
}
