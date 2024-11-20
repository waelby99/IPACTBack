package manajero.xp.manajeroxpmethodology.Services;

import manajero.xp.manajeroxpmethodology.Entities.manajero.Bug;
import manajero.xp.manajeroxpmethodology.Entities.manajero.BugStatus;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Iteration;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Task;

import java.util.List;
import java.util.Map;

public interface IBugService {
    public Bug addBug(Bug b);

    public List<Bug> getAllBugs() ;
    public Bug getBugById(String id);
    public void deleteBug(  String id);
    public Bug updateBug(Bug b);
    public Bug addBugAndAssignBugToTask(String id, Bug bug);
    public Task getTaskForBug(String bugId);

    public Map<String, Long> getBugsCountByStatus();
    long countBugsByStatus(BugStatus status);

    public long countTotalBugs();

    public double getAverageBugProgress();
    public Map<String, Long> getBugsCountPerTask();
    }
