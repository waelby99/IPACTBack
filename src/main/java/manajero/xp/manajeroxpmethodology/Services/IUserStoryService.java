package manajero.xp.manajeroxpmethodology.Services;

import manajero.xp.manajeroxpmethodology.Entities.manajero.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IUserStoryService {
    public UserStory createUserStory(UserStory userStory);
    public List<UserStory> getAllUserStories();
    public UserStory getUserStoryById(String id);
    public void deleteUserStory(String id);
    public UserStory updateUserStory(UserStory userStory);
    public UserStory addUserStoryAndAssignUserStoryToIteration(String id,UserStory userStory);
    public Iteration getIterationOfUserStory(String userStoryId) ;
    public List<Task> getTasksOfUserStory(String userStoryId) ;

    public int getTasksCount(String userStoryId) ;

    public long countByStatus(Status status);
    public long countCompletedUserStories();
    public long countTotalUserStories();

    public double getAverageTasksPerUserStory();

    public long countUserStoriesByPriority(UserStoryPriority priority);

    Map<String, Long> getUserStoryStatusDistribution();
    Map<String, Long> getUserStorypriorityDistribution();
    public UserStory updateUserStorywithiIteration(UserStory u, String iterationId);

}
