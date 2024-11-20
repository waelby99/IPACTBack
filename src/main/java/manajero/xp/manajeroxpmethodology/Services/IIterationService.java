package manajero.xp.manajeroxpmethodology.Services;

import manajero.xp.manajeroxpmethodology.Entities.manajero.Iteration;
import manajero.xp.manajeroxpmethodology.Entities.manajero.UserStory;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IIterationService {
    public Iteration addIteration(Iteration iteration);
    public List<Iteration> getAllIterations();
    public Iteration getIterationById(String id);
    public void deleteIteration(String id);
    public Iteration updateIteration(Iteration iteration);
    public Iteration addIterationAndAssignIterationToProject(String id,Iteration iteration);
    public List<UserStory> getUserStoriesOfIteration(String iterationId) ;

    public long getTotalIterations() ;
    public int getUserStoriesCount(String iterationId);
    // KPI methods
    Optional<String> getIterationDuration(String iterationId);
    double getIterationProgressPercentage(String iterationId);
    boolean isIterationOverdue(String iterationId);


    public Map<String, Integer> getCompletedUserStoriesPerIteration() ;

    public Map<String, Object> getMultipleXAxisChartData();

    public Map<String, Object> getIterationProcessData() ;
    }