package manajero.xp.manajeroxpmethodology.Repositories;


import manajero.xp.manajeroxpmethodology.Entities.manajero.Status;
import manajero.xp.manajeroxpmethodology.Entities.manajero.UserStory;
import manajero.xp.manajeroxpmethodology.Entities.manajero.UserStoryPriority;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserStoryRepository extends MongoRepository<UserStory, String> {
    List<UserStory> findByIterationId(String iterationId);

    @Query("{ '_id': ?0 }")
    UserStory findUserStoryWithTasks(String userStoryId);
    long countByStatus(Status status);
    long countByPriority(UserStoryPriority priority);

    default long countCompletedUserStories() {
        return countByStatus(Status.valueOf("DONE"));
    }

    default long countTotalUserStories() {
        return count();
    }

    @Query("{}")
    List<UserStory> findAllUserStoriesWithTasks();
}
