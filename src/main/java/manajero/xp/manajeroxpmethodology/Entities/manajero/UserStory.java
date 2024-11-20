package manajero.xp.manajeroxpmethodology.Entities.manajero;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
@Builder
@Document(collection = "userstories")
public class UserStory {
    @Id
    private String id;
    private String title;
    private String description;
    private UserStoryPriority priority;
    private Status status;

    @DBRef
    @JsonManagedReference
    private List<Task> tasks = new ArrayList<>();

    @DBRef
    @JsonBackReference
    private Iteration iteration;

    // Custom getter with default value
    public Status getStatus() {
        return status != null ? status : Status.TO_DO;  // Default to a valid status if null
    }
}
