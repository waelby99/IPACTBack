package manajero.xp.manajeroxpmethodology.Entities.manajero;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
@Builder
@Document(collection = "iterations")
public class Iteration {
    @Id
    private String id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;

    @DBRef
    @JsonManagedReference
    private List<UserStory> userStories = new ArrayList<>();  // Initialize to avoid null

    @DBRef
    private Project project;



    // Formatters for date and date-time
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Calculate the duration of the iteration
    public Optional<Duration> getDuration() {
        try {
            LocalDateTime start = parseDate(startDate);
            LocalDateTime end = parseDate(endDate);
            return Optional.of(Duration.between(start, end));
        } catch (DateTimeParseException e) {
            // Log the exception or handle it appropriately
            System.err.println("Date parsing error: " + e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            // Handle other exceptions
            System.err.println("Unexpected error: " + e.getMessage());
            return Optional.empty();
        }
    }

    // Helper method to parse date
    private LocalDateTime parseDate(String dateStr) {
        try {
            // Try parsing with date-time format first
            return LocalDateTime.parse(dateStr, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            // If it fails, fall back to date-only format
            return LocalDate.parse(dateStr, dateFormatter).atStartOfDay();
        }
    }

    // Format the duration into days
    public String getFormattedDurationInDays() {
        Optional<Duration> duration = getDuration();
        return duration.map(this::formatDuration).orElse("Invalid duration");
    }

    // Helper method to format duration in days
    private String formatDuration(Duration duration) {
        long days = duration.toDays(); // Get total days
        return days + " days";
    }

    // Calculate progress percentage based on completed user stories
    public double getProgressPercentage() {
        if (userStories.isEmpty()) {
            return 0.0;
        }
        long completedCount = userStories.stream()
                .filter(story -> Status.DONE.equals(story.getStatus()))
                .count();
        return (completedCount * 100.0) / userStories.size();
    }

    // Check if the iteration is overdue
    public boolean isOverdue() {
        try {
            LocalDateTime end = parseDate(endDate);
            return end.isBefore(LocalDateTime.now());
        } catch (Exception e) {
            return false;
        }
    }
}
