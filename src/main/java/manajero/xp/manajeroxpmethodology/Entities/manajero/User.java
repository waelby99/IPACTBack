package manajero.xp.manajeroxpmethodology.Entities.manajero;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.management.relation.Role;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
@Builder
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String email;
    private String matriculate;
    private boolean resetPassword;
    private int failedAttempts;
    private String lastName;
    private String phoneNumber;
    @DBRef
    private List<Task> tasks;
}
