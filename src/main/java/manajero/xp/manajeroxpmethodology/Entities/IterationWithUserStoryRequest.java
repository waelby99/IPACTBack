package manajero.xp.manajeroxpmethodology.Entities;

import lombok.Getter;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Iteration;
import manajero.xp.manajeroxpmethodology.Entities.manajero.UserStory;

@Getter
public class IterationWithUserStoryRequest {
    private Iteration iteration;
    private UserStory userStory;

    public void setIteration(Iteration iteration) {
        this.iteration = iteration;
    }

    public void setUserStory(UserStory userStory) {
        this.userStory = userStory;
    }

    // Getters and setters
}

