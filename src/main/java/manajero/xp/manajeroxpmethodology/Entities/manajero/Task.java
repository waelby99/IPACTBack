package manajero.xp.manajeroxpmethodology.Entities.manajero;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
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
@Document(collection = "tasks")
public class Task  {
    @Id
    private String id;
    private String title;
    private String description;
    private Status status;
     // Add this field


    @DBRef
    @JsonBackReference
    private UserStory userStory;

    @DBRef
    @JsonManagedReference

    private List<Bug> bugs = new ArrayList<>();

    @DBRef
    private User user;
}
