package manajero.xp.manajeroxpmethodology.Controller;

import lombok.AllArgsConstructor;
import manajero.xp.manajeroxpmethodology.Entities.IterationWithUserStoryRequest;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Iteration;
import manajero.xp.manajeroxpmethodology.Entities.manajero.UserStory;
import manajero.xp.manajeroxpmethodology.Services.IIterationService;
import manajero.xp.manajeroxpmethodology.Services.IterationServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/ManajeroBackend/api/iteration")
public class IterationController {
    private  IIterationService iIterationService;


    @GetMapping
    public List<Iteration> getAllIterations() {
        return iIterationService.getAllIterations();
    }

    @PostMapping
    public Iteration addIteration(@RequestBody Iteration i) {
        return iIterationService.addIteration(i);
    }

    @DeleteMapping("/{id}")
    public void deleteIteration(@PathVariable String id) {
        iIterationService.deleteIteration(id);
    }

    @PutMapping("/{id}")
    public Iteration updateIteration(@RequestBody Iteration i) {
        return iIterationService.updateIteration(i);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Iteration> getIterationById(@PathVariable String id) {
        Iteration iteration = iIterationService.getIterationById(id);
        return iteration != null ? ResponseEntity.ok(iteration) : ResponseEntity.notFound().build();
    }



    @PostMapping("/addIterationAndAssignIterationToProject/{id}" )
    public Iteration addIterationAndAssignIterationToProject (@RequestBody Iteration i, @PathVariable String id) {
        return iIterationService.addIterationAndAssignIterationToProject(id , i);
    }
    @GetMapping("/{id}/userStories")
    public List<UserStory> getUserStoriesOfIteration(@PathVariable String id) {
        return iIterationService.getUserStoriesOfIteration(id);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalIterations() {
        long totalIterations = iIterationService.getTotalIterations();
        return ResponseEntity.ok(totalIterations);
    }

    // Method to get the count of user stories in a specific iteration
    @GetMapping("/{iterationId}/userStories/count")
    public ResponseEntity<Integer> getUserStoriesCount(@PathVariable String iterationId) {
        int userStoriesCount = iIterationService.getUserStoriesCount(iterationId);
        return ResponseEntity.ok(userStoriesCount);
    }
    @GetMapping("/{id}/duration")
    public Optional<String> getIterationDuration(@PathVariable String id) {
        return iIterationService.getIterationDuration(id);
    }

    @GetMapping("/{id}/progress")
    public double getIterationProgressPercentage(@PathVariable String id) {
        return iIterationService.getIterationProgressPercentage(id);
    }

    @GetMapping("/{id}/overdue")
    public boolean isIterationOverdue(@PathVariable String id) {
        return iIterationService.isIterationOverdue(id);
    }


    @GetMapping("/completed-user-stories")
    public Map<String, Integer> getCompletedUserStoriesPerIteration() {
        return iIterationService.getCompletedUserStoriesPerIteration();
    }

    @GetMapping("/multiple-x-axis")
    public ResponseEntity<Map<String, Object>> getMultipleXAxisChartData() {
        Map<String, Object> chartData = iIterationService.getMultipleXAxisChartData();
        return ResponseEntity.ok(chartData);
    }

    @GetMapping("/process-data")
    public ResponseEntity<Map<String, Object>> getIterationProcessData() {
        Map<String, Object> chartData= iIterationService.getIterationProcessData();
        return ResponseEntity.ok(chartData);
    }
}
