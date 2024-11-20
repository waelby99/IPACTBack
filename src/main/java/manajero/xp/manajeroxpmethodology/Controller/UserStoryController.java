package manajero.xp.manajeroxpmethodology.Controller;
import lombok.AllArgsConstructor;
import manajero.xp.manajeroxpmethodology.Entities.manajero.*;

import manajero.xp.manajeroxpmethodology.Services.IUserStoryService;
import manajero.xp.manajeroxpmethodology.Services.UserStoryServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/ManajeroBackend/api/userStory")
public class UserStoryController {
    private final IUserStoryService iUserStoryService;
    private UserStoryServiceImp userStoryServiceImp;

    @GetMapping
    public List<UserStory> getAllUserStories() {
        return iUserStoryService.getAllUserStories();
    }



    @DeleteMapping("/{id}")
    public void deleteUserStory(@PathVariable String id) {
        iUserStoryService.deleteUserStory(id);
    }

    @PutMapping("/updateUserStory")
    public UserStory updateUserStory(@RequestBody UserStory u) {
        return iUserStoryService.updateUserStory(u);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserStory> updateUserStoryWITHITAERATION(
            @PathVariable String id,
            @RequestBody UserStory userStory,
            @RequestParam(required = false) String iterationId) {
        UserStory updatedUserStory = iUserStoryService.updateUserStorywithiIteration(userStory, iterationId);
        return ResponseEntity.ok(updatedUserStory);
    }

    @GetMapping("/{id}")
    public UserStory getUserStoryById(@PathVariable String id) {
        return iUserStoryService.getUserStoryById(id);
    }


    @PostMapping("/create")
    public ResponseEntity<UserStory> createUserStory(@RequestBody UserStory userStory) {
        UserStory createdUserStory = iUserStoryService.createUserStory(userStory);
        return ResponseEntity.ok(createdUserStory);
    }


    @GetMapping("/{userStoryId}/iteration")
    public ResponseEntity<Iteration> getIterationOfUserStory(@PathVariable String userStoryId) {
        Iteration iteration = iUserStoryService.getIterationOfUserStory(userStoryId);
        return ResponseEntity.ok(iteration);
    }


    @GetMapping("/{userStoryId}/task")
    public List<Task> getTasksOfUserStory(@PathVariable String userStoryId) {
        return iUserStoryService.getTasksOfUserStory(userStoryId);
    }
    @PostMapping("/addUserStoryAndAssignUserStoryToIteration/{id}")
        public UserStory addUserStoryAndAssignUserStoryToIteration(@RequestBody UserStory us, @PathVariable String id) {
        return iUserStoryService.addUserStoryAndAssignUserStoryToIteration(id , us);

    }
    // Method to get the count of tasks in a specific user story
    @GetMapping("/{userStoryId}/tasks/count")
    public ResponseEntity<Integer> getTasksCount(@PathVariable String userStoryId) {
        int tasksCount = iUserStoryService.getTasksCount(userStoryId);
        return ResponseEntity.ok(tasksCount);
    }

    // Method to get the distribution of user story statuses
    @GetMapping("/count/status")
    public long countByStatus(@RequestParam Status status) {
        return iUserStoryService.countByStatus(status);
    }

    @GetMapping("/count/completed")
    public long countCompletedUserStories() {
        return iUserStoryService.countCompletedUserStories();
    }

    @GetMapping("/count/total")
    public long countTotalUserStories() {
        return iUserStoryService.countTotalUserStories();
    }

    @GetMapping("/count/priority")
    public long countUserStoriesByPriority(@RequestParam UserStoryPriority priority) {
        return iUserStoryService.countUserStoriesByPriority(priority);
    }
    // Method to get the average tasks per user story
    @GetMapping("/averageTasks")
    public ResponseEntity<Double> getAverageTasksPerUserStory() {
        double averageTasks = iUserStoryService.getAverageTasksPerUserStory();
        return ResponseEntity.ok(averageTasks);
    }
    @GetMapping("/status-distribution")
    public Map<String, Long> getUserStoryStatusDistribution() {
        return iUserStoryService.getUserStoryStatusDistribution();
    }

    @GetMapping("/priority-distribution")
    public Map<String, Long> getUserStorypriorityDistribution() {
        return iUserStoryService.getUserStorypriorityDistribution();
    }
}
