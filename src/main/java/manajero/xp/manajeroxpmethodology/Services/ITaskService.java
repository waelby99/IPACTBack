package manajero.xp.manajeroxpmethodology.Services;

import manajero.xp.manajeroxpmethodology.Entities.manajero.*;

import java.util.List;
import java.util.Map;

public interface ITaskService {
    public Task addTask(Task task);
    public List<Task> getAllTasks();
    public Task getTaskById(String id);
    public void deleteTask(String id);
    public Task updateTask(Task task);
    public Task addTaskAndAssignTaskToUserStory(String id, Task task);
/*
    public Task updateTaskwithUserStory(String id, Task task, String userStoryId);
*/

    public UserStory getUserStoryOfTask(String taskId) ;

    public List<Bug> getBugsForTask(String taskId);
    public int getBugsCount(String taskId) ;
    long getTaskCountByStatus(Status status);
    Map<String, Long> getTaskStatusDistribution();
    public Map<String, Long> getBugsInIncompleteTasks();

    public long countTotaltasks();



}
