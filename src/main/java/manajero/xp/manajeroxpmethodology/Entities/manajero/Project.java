package manajero.xp.manajeroxpmethodology.Entities.manajero;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
@Builder
@Document(collection = "projects")
public class Project {
    @Id
    private String id;
    private String title;
    private Status status;
    private String statementwork;
    private String datesubmitted;

    private boolean archived;

    @DBRef
    private List<Iteration> iterations;

}
