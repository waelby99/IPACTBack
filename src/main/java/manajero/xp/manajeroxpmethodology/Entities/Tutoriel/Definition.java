package manajero.xp.manajeroxpmethodology.Entities.Tutoriel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "definition")
public class Definition {
    @Id
    private String id;
    private String Titre;
    private String description;

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }
    // Getters and Setters
}
