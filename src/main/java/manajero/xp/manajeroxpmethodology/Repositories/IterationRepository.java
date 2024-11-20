package manajero.xp.manajeroxpmethodology.Repositories;


import manajero.xp.manajeroxpmethodology.Entities.manajero.Iteration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface IterationRepository extends MongoRepository<Iteration, String> {
    long count();
    @Query("{ '_id': ?0 }")
    Iteration findIterationWithUserStories(String iterationId);
}
