package manajero.xp.manajeroxpmethodology.Services;

import lombok.AllArgsConstructor;
import manajero.xp.manajeroxpmethodology.Entities.manajero.*;
import manajero.xp.manajeroxpmethodology.Repositories.BugRepository;
import manajero.xp.manajeroxpmethodology.Repositories.TaskRepository;
import manajero.xp.manajeroxpmethodology.Repositories.UserStoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImp implements ITaskService {
    private final TaskRepository taskRepository;
    private BugRepository bugRepository; // Assume BugRepository exists

    private UserStoryRepository userStoryRepository;
    @Override
    public Task addTask(Task t) {
        return taskRepository.save(t);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(String id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteTask(String id) {
        // Fetch the Task to be deleted
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        // Fetch the associated UserStory
        UserStory userStory = task.getUserStory();
        if (userStory != null) {
            // Remove the Task from the UserStory's list of tasks
            userStory.getTasks().remove(task);
            userStoryRepository.save(userStory);  // Save changes to UserStory
        }

        // Remove the associated Bugs
        for (Bug bug : task.getBugs()) {
            bugRepository.delete(bug);  // Directly delete the Bug
        }

        // Finally, delete the Task
        taskRepository.deleteById(id);
    }

    @Override
    public Task updateTask(Task t) {
        Optional<Task> existingTask = taskRepository.findById(t.getId());
        if (existingTask.isPresent()) {
            Task task = existingTask.get();

            // Update fields of userStory
            task.setTitle(t.getTitle());
            task.setDescription(t.getDescription());
            task.setStatus(t.getStatus());

            // Only update the iteration if it is not null in the incoming update
            if (t.getUserStory() != null) {
                task.setUserStory(t.getUserStory());
            }

            // Save updated user story
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("User Story not found with id " + t.getId());
        }
    }

    @Override
    public Task addTaskAndAssignTaskToUserStory(String id, Task task) {
        // Find the UserStory by id
        UserStory userStory = userStoryRepository.findById(id).orElse(null);

        if (userStory != null) {
            // Set the UserStory in the Task
            task.setUserStory(userStory);

            // Save the Task first
            Task savedTask = taskRepository.save(task);

            // Add the saved Task to the UserStory's tasks list
            userStory.getTasks().add(savedTask);

            // Save the UserStory to update the tasks list
            userStoryRepository.save(userStory);

            return savedTask;
        } else {
            return null;
        }
    }
/*
    @Override
    public Task updateTaskwithUserStory(String id, Task task, String userStoryId) {
        // Retrieve the existing task
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Update task fields
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());

        // Update the UserStory reference if provided
        if (userStoryId != null) {
            UserStory userStory = userStoryRepository.findById(userStoryId)
                    .orElseThrow(() -> new RuntimeException("Task not found"));
            existingTask.setUserStory(userStory);
        }

        // Save and return the updated task
        return taskRepository.save(existingTask);
    }
*/

    @Override
    public UserStory getUserStoryOfTask(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (task.getUserStory() == null) {
            throw new RuntimeException("UserStory not found for the given Task");
        }

        return userStoryRepository.findById(task.getUserStory().getId())
                .orElseThrow(() -> new RuntimeException("UserStory not found for the given Task"));
    }
    @Override
    public List<Bug> getBugsForTask(String taskId) {
        // Assuming Bug entity has a taskId field
        return bugRepository.findByTaskId(taskId);
    }

    @Override
    public int getBugsCount(String taskId) {
        Task task = taskRepository.findTaskWithBugs(taskId);
        return task.getBugs().size();
    }

    @Override
    public long getTaskCountByStatus(Status status) {
        return taskRepository.countByStatus(status);
    }

    @Override
    public Map<String, Long> getTaskStatusDistribution() {
        Map<String, Long> distribution = new HashMap<>();
        for (Status status : Status.values()) {
            distribution.put(status.name(), taskRepository.countByStatus(status));
        }
        return distribution;
    }

    @Override
    public Map<String, Long> getBugsInIncompleteTasks() {
        List<Task> incompleteTasks = taskRepository.findByStatusNot(Status.IN_PROGRESS);
        return incompleteTasks.stream()
                .collect(Collectors.groupingBy(
                        Task::getTitle,
                        Collectors.summingLong(task -> (long) task.getBugs().size())
                ));
    }

    @Override
    public long countTotaltasks() {
        return taskRepository.countTotalTasks();
    }


}
