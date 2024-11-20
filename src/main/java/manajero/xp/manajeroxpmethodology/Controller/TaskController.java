package manajero.xp.manajeroxpmethodology.Controller;
import lombok.AllArgsConstructor;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Bug;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Status;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Task;
import manajero.xp.manajeroxpmethodology.Entities.manajero.UserStory;
import manajero.xp.manajeroxpmethodology.Services.ITaskService;
import manajero.xp.manajeroxpmethodology.Services.TaskServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/ManajeroBackend/api/task")
public class TaskController {
    private final ITaskService iTaskService;
    @GetMapping()
    public List<Task> getAllTasks() {
        return iTaskService.getAllTasks();
    }

    @PostMapping()
    public Task addTask(@RequestBody Task t) {
        return iTaskService.addTask(t);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        iTaskService.deleteTask(id);
    }

    @PutMapping("/updateTask")
    public Task updateTask(@RequestBody Task t) {
        return iTaskService.updateTask(t);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id) {
        return iTaskService.getTaskById(id);
    }


    @GetMapping("/{taskId}/userStory")
    public ResponseEntity<UserStory> getUserStoryOfTask(@PathVariable String taskId) {
        UserStory userStory = iTaskService.getUserStoryOfTask(taskId);
        return ResponseEntity.ok(userStory);
    }


    @PostMapping("/addTaskAndAssignTaskToUserStory/{id}" )
    public Task addTaskAndAssignTaskToUserStory (@RequestBody Task t, @PathVariable String id) {
        return iTaskService.addTaskAndAssignTaskToUserStory(id , t);
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTaskWithUserStory(
            @PathVariable String id,
            @RequestBody Task task,
            @RequestParam(required = false) String userStoryId) {
        Task updatedTask = iTaskService.updateTaskwithUserStory(id, task, userStoryId);
        return ResponseEntity.ok(updatedTask);
    }


 */
    @GetMapping("/{taskId}/bug")
    public List<Bug> getBugsByTaskId(@PathVariable String taskId) {
        return iTaskService.getBugsForTask(taskId);
    }

    @GetMapping("/{taskId}/bugs/count")
    public int getBugsCount(@PathVariable String taskId) {
        return iTaskService.getBugsCount(taskId);
    }


    @GetMapping("/count/total")
    public long countTotalTasks() {
        return iTaskService.countTotaltasks();
    }
    @GetMapping("/count")
    public long getTaskCountByStatus(@RequestParam Status status) {
        return iTaskService.getTaskCountByStatus(Status.valueOf(String.valueOf(status)));
    }

    @GetMapping("/status-distribution")
    public Map<String, Long> getTaskStatusDistribution() {
        return iTaskService.getTaskStatusDistribution();
    }

    @GetMapping("/incomplete-tasks-with-bugs")
    public Map<String, Long> getBugsInIncompleteTasks() {
        return iTaskService.getBugsInIncompleteTasks();
    }
}
