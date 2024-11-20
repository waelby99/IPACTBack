package manajero.xp.manajeroxpmethodology.Services;

import lombok.AllArgsConstructor;
import manajero.xp.manajeroxpmethodology.Entities.manajero.*;

import manajero.xp.manajeroxpmethodology.Repositories.IterationRepository;
import manajero.xp.manajeroxpmethodology.Repositories.UserStoryRepository;

import manajero.xp.manajeroxpmethodology.Repositories.IterationRepository;
import manajero.xp.manajeroxpmethodology.Repositories.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
public class IterationServiceImp implements IIterationService {
    private  IterationRepository iterationRepository;
    private ProjectRepository projectRepository;
    private UserStoryRepository userStoryRepository;


    @Override
    public Iteration addIteration(Iteration i) {
        return iterationRepository.save(i);
    }

    @Override
    public List<Iteration> getAllIterations() {
        return iterationRepository.findAll();
    }

    @Override
    public Iteration getIterationById(String id) {
        return iterationRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteIteration(String id) {
        iterationRepository.deleteById(id);
    }

    @Override
    public Iteration updateIteration(Iteration i) {
        return iterationRepository.save(i);
    }


    @Override
    public List<UserStory> getUserStoriesOfIteration(String iterationId) {
        return userStoryRepository.findByIterationId(iterationId);
    }

    @Override
    public long getTotalIterations() {
        return iterationRepository.count();

    }

    @Override
    public int getUserStoriesCount(String iterationId) {
        Iteration iteration = iterationRepository.findIterationWithUserStories(iterationId);
        return iteration.getUserStories().size();
    }

    @Override
    public Optional<String> getIterationDuration(String iterationId) {
        return iterationRepository.findById(iterationId)
                .flatMap(iteration -> iteration.getDuration()
                        .map(this::formatDuration)); // Format duration here
    }

    // Helper method to format duration in days
    private String formatDuration(Duration duration) {
        long days = duration.toDays(); // Get total days
        return days + " days";
    }


    @Override
    public double getIterationProgressPercentage(String iterationId) {
        return iterationRepository.findById(iterationId)
                .map(Iteration::getProgressPercentage)
                .orElse(0.0);
    }

    @Override
    public boolean isIterationOverdue(String iterationId) {
        return iterationRepository.findById(iterationId)
                .map(Iteration::isOverdue)
                .orElse(false);
    }

    @Override
    public Map<String, Integer> getCompletedUserStoriesPerIteration() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Integer> completedStoriesMap = new HashMap<>();

        List<Iteration> iterations = iterationRepository.findAll();

        for (Iteration iteration : iterations) {
            LocalDate startDate = LocalDate.parse(iteration.getStartDate(), formatter);
            LocalDate endDate = LocalDate.parse(iteration.getEndDate(), formatter);

            for (UserStory userStory : iteration.getUserStories()) {
                // Check if all tasks are completed
                boolean allTasksCompleted = userStory.getTasks().stream()
                        .filter(task -> task != null) // Filter out null tasks
                        .allMatch(task -> Status.DONE.equals(task.getStatus())); // Use equals for comparison

                if (allTasksCompleted) {
                    // Use the start date of the iteration as a key for completed stories
                    String dateKey = startDate.format(formatter);
                    completedStoriesMap.put(dateKey, completedStoriesMap.getOrDefault(dateKey, 0) + 1);
                }
            }
        }

        return completedStoriesMap;
    }

    @Override
    public Map<String, Object> getMultipleXAxisChartData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Object> chartData = new HashMap<>();

        List<String> startDates = new ArrayList<>();
        List<String> endDates = new ArrayList<>();
        List<Integer> userStoriesCount = new ArrayList<>();

        List<Iteration> iterations = iterationRepository.findAll();
        for (Iteration iteration : iterations) {
            LocalDate startDate = LocalDate.parse(iteration.getStartDate(), formatter);
            LocalDate endDate = LocalDate.parse(iteration.getEndDate(), formatter);

            startDates.add(startDate.format(formatter));
            endDates.add(endDate.format(formatter));

            // Count the number of user stories
            int count = iteration.getUserStories().size();
            userStoriesCount.add(count);
        }

        chartData.put("startDates", startDates);
        chartData.put("endDates", endDates);
        chartData.put("userStoriesCount", userStoriesCount);

        return chartData;
    }

    @Override
    public Map<String, Object> getIterationProcessData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Object> chartData = new HashMap<>();

        Map<String, int[]> dateMap = new TreeMap<>();
        List<String> iterationStartDates = new ArrayList<>();
        List<String> iterationEndDates = new ArrayList<>();

        List<Iteration> iterations = iterationRepository.findAll();

        for (Iteration iteration : iterations) {
            LocalDate startDate = LocalDate.parse(iteration.getStartDate(), formatter);
            LocalDate endDate = LocalDate.parse(iteration.getEndDate(), formatter);

            // Collect data daily within the iteration period
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                String dateKey = date.format(formatter);

                // Initialize counts if not present
                if (!dateMap.containsKey(dateKey)) {
                    dateMap.put(dateKey, new int[3]); // 0: To Do, 1: In Progress, 2: Done
                }

                // Count the number of user stories in each status
                for (UserStory userStory : iteration.getUserStories()) {
                    switch (userStory.getStatus()) {
                        case TO_DO:
                            dateMap.get(dateKey)[0]++;
                            break;
                        case IN_PROGRESS:
                            dateMap.get(dateKey)[1]++;
                            break;
                        case DONE:
                            dateMap.get(dateKey)[2]++;
                            break;
                    }
                }
            }

            // Record iteration start and end dates
            iterationStartDates.add(startDate.format(formatter));
            iterationEndDates.add(endDate.format(formatter));
        }

        // Prepare final lists for chart
        List<String> dates = new ArrayList<>();
        List<Integer> toDoCounts = new ArrayList<>();
        List<Integer> inProgressCounts = new ArrayList<>();
        List<Integer> doneCounts = new ArrayList<>();

        for (Map.Entry<String, int[]> entry : dateMap.entrySet()) {
            dates.add(entry.getKey());
            toDoCounts.add(entry.getValue()[0]);
            inProgressCounts.add(entry.getValue()[1]);
            doneCounts.add(entry.getValue()[2]);
        }

        chartData.put("dates", dates);
        chartData.put("toDoCounts", toDoCounts);
        chartData.put("inProgressCounts", inProgressCounts);
        chartData.put("doneCounts", doneCounts);
        chartData.put("iterationStartDates", iterationStartDates);
        chartData.put("iterationEndDates", iterationEndDates);

        return chartData;
    }



    @Override
    public Iteration addIterationAndAssignIterationToProject(String projectId, Iteration iteration) {
        // Find the project by its ID
        Project project = projectRepository.findById(projectId).orElse(null);

        // Check if the project exists
        if (project != null) {
            // Set the project in the iteration
            iteration.setProject(project);

            // Add the iteration to the project's list of iterations
            project.getIterations().add(iteration);

            // Save the iteration to the iteration repository
            iterationRepository.save(iteration);

            // Save the project to update its list of iterations
            projectRepository.save(project);

            return iteration;
        } else {
            // If the project doesn't exist, return null
            return null;
        }
    }


}

