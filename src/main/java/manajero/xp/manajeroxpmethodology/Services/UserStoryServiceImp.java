package manajero.xp.manajeroxpmethodology.Services;

import lombok.AllArgsConstructor;
import manajero.xp.manajeroxpmethodology.Entities.manajero.*;

import manajero.xp.manajeroxpmethodology.Repositories.*;

import manajero.xp.manajeroxpmethodology.Entities.manajero.UserStory;
import manajero.xp.manajeroxpmethodology.Repositories.IterationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class UserStoryServiceImp implements IUserStoryService {

    private final UserStoryRepository userStoryRepository;
    private IterationRepository iterationRepository;
    private TaskRepository taskRepository ;
    private BugRepository bugRepository; // Assume BugRepository exists

    private final Logger logger = LoggerFactory.getLogger(UserStoryServiceImp.class);

    public UserStory addUserStory(UserStory u) {
        return userStoryRepository.save(u);
    }

    @Override
    public UserStory createUserStory(UserStory userStory) {
        return userStoryRepository.save(userStory);
    }

    @Override
    public List<UserStory> getAllUserStories() {
        return userStoryRepository.findAll();
    }

    @Override
    public UserStory getUserStoryById(String id) {
        return userStoryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUserStory(String id) {
        UserStory story = userStoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserStory not found"));

        Iteration iteration = story.getIteration();
        if (iteration != null) {
            iteration.getUserStories().remove(story);
            iterationRepository.save(iteration);
        }

        // Remove the associated Tasks and Bugs
        for (Task task : story.getTasks()) {
            // Delete all Bugs associated with the Task
            for (Bug bug : task.getBugs()) {
                bugRepository.delete(bug);  // Delete Bug
            }
            taskRepository.delete(task);  // Delete Task
        }

        userStoryRepository.deleteById(id);  // Finally, delete the UserStory
    }


    @Override
    public UserStory updateUserStory(UserStory updatedUserStory) {
        Optional<UserStory> existingUserStory = userStoryRepository.findById(updatedUserStory.getId());
        if (existingUserStory.isPresent()) {
            UserStory userStory = existingUserStory.get();

            // Update fields of userStory
            userStory.setTitle(updatedUserStory.getTitle());
            userStory.setDescription(updatedUserStory.getDescription());
            userStory.setStatus(updatedUserStory.getStatus());
            userStory.setPriority(updatedUserStory.getPriority());

            // Only update the iteration if it is not null in the incoming update
            if (updatedUserStory.getIteration() != null) {
                userStory.setIteration(updatedUserStory.getIteration());
            }

            // Save updated user story
            return userStoryRepository.save(userStory);
        } else {
            throw new RuntimeException("User Story not found with id " + updatedUserStory.getId());
        }
    }


    @Override
    public Iteration getIterationOfUserStory(String userStoryId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new RuntimeException("UserStory not found"));
        return userStory.getIteration();
    }


    @Override
    public List<Task> getTasksOfUserStory(String userStoryId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new RuntimeException("UserStory not found"));
        return userStory.getTasks() != null ? userStory.getTasks() : new ArrayList<>();
    }

    @Override
    public int getTasksCount(String userStoryId) {
        UserStory userStory = userStoryRepository.findUserStoryWithTasks(userStoryId);
        return userStory.getTasks().size();    }

    @Override
    public long countByStatus(Status status) {
        return userStoryRepository.countByStatus(status);

    }

    @Override
    public long countCompletedUserStories() {
        return userStoryRepository.countCompletedUserStories();
    }

    @Override
    public long countTotalUserStories() {
        return userStoryRepository.countTotalUserStories();
    }


    @Override
    public double getAverageTasksPerUserStory() {
        List<UserStory> userStories = userStoryRepository.findAllUserStoriesWithTasks();
        return userStories.stream()
                .mapToInt(us -> us.getTasks().size())
                .average()
                .orElse(0);

    }

    @Override
    public long countUserStoriesByPriority(UserStoryPriority priority) {
        return userStoryRepository.countByPriority(priority);

    }

    @Override
    public Map<String, Long> getUserStoryStatusDistribution() {
        Map<String, Long> distribution = new HashMap<>();
        for (Status status : Status.values()) {
            distribution.put(status.name(), userStoryRepository.countByStatus(status));
        }
        return distribution;

    }

    @Override
    public Map<String, Long> getUserStorypriorityDistribution() {
        Map<String, Long> distribution = new HashMap<>();
        for (UserStoryPriority priority : UserStoryPriority.values()) {
            distribution.put(priority.name(), userStoryRepository.countByPriority(priority));
        }
        return distribution;

    }

    @Override
    public UserStory updateUserStorywithiIteration(UserStory u, String iterationId) {
        if (iterationId != null) {
            Iteration iteration = iterationRepository.findById(iterationId).orElse(null);
            if (iteration != null) {
                u.setIteration(iteration);
            }
        }
        return userStoryRepository.save(u);
    }


    @Override
    public UserStory addUserStoryAndAssignUserStoryToIteration(String id, UserStory userStory) {
        // Find the Iteration by id
        Iteration iteration = iterationRepository.findById(id).orElse(null);

        if (iteration != null) {
            // Set the Iteration in the UserStory
            userStory.setIteration(iteration);

            // Save the UserStory first
            UserStory savedUserStory = userStoryRepository.save(userStory);

            // Add the saved UserStory to the Iteration's userStories list
            iteration.getUserStories().add(savedUserStory);

            // Save the Iteration to update the userStories list
            iterationRepository.save(iteration);

            return savedUserStory;
        } else {
            return null;
        }
    }

}

