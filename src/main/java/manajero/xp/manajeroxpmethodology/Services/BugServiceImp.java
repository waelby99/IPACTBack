package manajero.xp.manajeroxpmethodology.Services;

import lombok.AllArgsConstructor;
import manajero.xp.manajeroxpmethodology.Entities.manajero.*;
import manajero.xp.manajeroxpmethodology.Repositories.BugRepository;
import manajero.xp.manajeroxpmethodology.Repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class BugServiceImp implements IBugService{
    private BugRepository bugRepository;
    private TaskRepository taskRepository;
    @Override
    public Bug addBug(Bug b) {
        return bugRepository.save(b);
    }

    @Override
    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    @Override
    public Bug getBugById(String id) {
        return bugRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteBug(String id) {
        Bug bug = bugRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bug not found"));

        Task task = bug.getTask();
        if (task != null) {
            task.getBugs().remove(bug);
            taskRepository.save(task);
        }

        bugRepository.deleteById(id);
    }


    @Override
    public Bug updateBug(Bug b) {
        Optional<Bug> existingBug = bugRepository.findById(b.getId());
        if (existingBug.isPresent()) {
            Bug bug = existingBug.get();

            // Update fields of userStory
            bug.setTitle(b.getTitle());
            bug.setDescription(b.getDescription());
            bug.setProgress(b.getProgress());
            bug.setStatus(b.getStatus());

            // Only update the iteration if it is not null in the incoming update
            if (b.getTask() != null) {
                bug.setTask(b.getTask());
            }

            // Save updated user story
            return bugRepository.save(bug);
        } else {
            throw new RuntimeException("User Story not found with id " + b.getId());
        }
    }


    @Override
    public Bug addBugAndAssignBugToTask(String id, Bug bug) {
        // Find the Task by id
        Task task = taskRepository.findById(id).orElse(null);

        if (task != null) {
            // Set the Task in the Bug
            bug.setTask(task);

            // Save the Bug first
            Bug savedBug = bugRepository.save(bug);

            // Add the saved Bug to the Task's bugs list
            task.getBugs().add(savedBug);

            // Save the Task to update the bugs list
            taskRepository.save(task);

            return savedBug;
        } else {
            return null;
        }
    }
    @Override
    public Task getTaskForBug(String bugId) {
        Bug bug = bugRepository.findById(bugId).orElseThrow(() -> new RuntimeException("task not found"));
        return bug.getTask(); // Assuming getTask() returns the Task object
    }

    @Override
    public Map<String, Long> getBugsCountByStatus() {
        Map<String, Long> countByStatus = new HashMap<>();
        for (BugStatus status : BugStatus.values()) {
            countByStatus.put(status.name(), bugRepository.countByStatus(status));
        }
        return countByStatus;
    }


    @Override
    public long countBugsByStatus(BugStatus status) {
        return bugRepository.countByStatus(status);
    }

    @Override
    public long countTotalBugs() {
        return bugRepository.countTotalBug();
    }


    @Override
    public double getAverageBugProgress() {
        List<Bug> bugs = bugRepository.findAll();
        return bugs.isEmpty() ? 0 : bugs.stream()
                .mapToInt(Bug::getProgress)
                .average()
                .orElse(0);
    }

    @Override
    public Map<String, Long> getBugsCountPerTask() {
        List<Bug> bugs = bugRepository.findAll();
        return bugs.stream()
                .filter(bug -> bug.getTask() != null) // Ensure bug.getTask() is not null
                .collect(Collectors.groupingBy(
                        bug -> bug.getTask().getId(), // This will not throw NullPointerException now
                        Collectors.counting()
                ));
    }


}



