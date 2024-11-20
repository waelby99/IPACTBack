package manajero.xp.manajeroxpmethodology.Entities.manajero;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
@Builder
@Document(collection = "bugs")
public class Bug {
    @Id
    private String id;
    private String title;
    private String description;
    private String severity;
    private BugStatus status;
    private int progress;

    @DBRef
    @JsonBackReference
    private Task task;
}
