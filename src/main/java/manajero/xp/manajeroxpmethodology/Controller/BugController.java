package manajero.xp.manajeroxpmethodology.Controller;

import lombok.AllArgsConstructor;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Bug;
import manajero.xp.manajeroxpmethodology.Entities.manajero.BugStatus;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Task;
import manajero.xp.manajeroxpmethodology.Entities.manajero.UserStory;
import manajero.xp.manajeroxpmethodology.Services.IBugService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor

@RestController
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials="true")
@RequestMapping("/ManajeroBackend/api/bug")
public class BugController {
    IBugService iBugService;

    @GetMapping("/getAllBugs")
    public List<Bug> getAllBugs() {
        return iBugService.getAllBugs();
    }

    @PostMapping
    public Bug addBug(@RequestBody Bug b) {
        return iBugService.addBug(b);
    }
    @DeleteMapping("/{id}")
    public void deleteBug(@PathVariable String id) {
        iBugService.deleteBug(id);
    }
    @PutMapping("/updateBug")
    public Bug updateBug(@RequestBody Bug b ) {
        return iBugService.updateBug(b);
    }

    @GetMapping(value = "/{id}")
    public Bug getBugById(@PathVariable String id) {
        return iBugService.getBugById(id);
    }

    @PostMapping("/addBugAndAssignBugToTask/{id}" )
    public Bug addBugAndAssignBugToTask (@RequestBody Bug b, @PathVariable String id) {
        return iBugService.addBugAndAssignBugToTask(id , b);
    }
    @GetMapping("/{id}/task")
    public Task getTaskForBug(@PathVariable String id) {
        return iBugService.getTaskForBug(id);
    }


    @GetMapping("/countByStatus")
    public ResponseEntity<Long> countBugsByStatus(@RequestParam BugStatus status) {
        long count = iBugService.countBugsByStatus(status);
        return ResponseEntity.ok(count);
    }
    @GetMapping("/count/total")
    public long countTotalBugs() {
        return iBugService.countTotalBugs();
    }

    @GetMapping("/average-progress")
    public double getAverageBugProgress() {
        return iBugService.getAverageBugProgress();
    }

    @GetMapping("/status-distribution")
    public Map<String, Long> getBUGSStatusDistribution() {
        return iBugService.getBugsCountByStatus();
    }

    @GetMapping("/count-per-task")
    public Map<String, Long> getBugsCountPerTask() {
        return iBugService.getBugsCountPerTask();
    }
}
