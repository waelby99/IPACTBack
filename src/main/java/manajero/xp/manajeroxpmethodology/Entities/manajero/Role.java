package manajero.xp.manajeroxpmethodology.Entities.manajero;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
@Builder
@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    private String name;
    // Add other role-specific fields if necessary
}
