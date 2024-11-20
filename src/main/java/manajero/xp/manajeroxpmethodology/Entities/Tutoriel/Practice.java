package manajero.xp.manajeroxpmethodology.Entities.Tutoriel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "practice")
public class Practice {
    @Id
    private String id;
    private String name;
    private String definition;
    private List<String> benefits;
    private List<String> challenges;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public List<String> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<String> challenges) {
        this.challenges = challenges;
    }

    // Getters and Setters
}
